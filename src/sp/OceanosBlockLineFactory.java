package sp;

import java.math.BigDecimal;

import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMLine;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;

import reports.EnumBlockType;
import reports.Error;
import sp.spline.MaterialSpecLineAttributes;
import sp.spline.SPLine;
import sp.spline.SPLineDetail;
import sp.spline.SPLineMaterial;

public class OceanosBlockLineFactory
{
	private SP sp;
	private SPLineDetail emptyLine = new SPLineDetail();

	public OceanosBlockLineFactory(SP sp)
	{
		this.sp = sp;
	}

	private final String[] blProps = new String[] { "Oc9_Zone", "bl_sequence_no", "bl_quantity", "Oc9_Note",
			"Oc9_IsFromEAssembly", // � ��������� � ���������� sequence_no
									// ������ ���� ���������� ��������
			"Oc9_DisChangeFindNo", // � ��������� � ���������� sequence_no
									// ������ ���� ���������� ��������
			"oc9_KITName", "bl_item_uom_tag", "Oc9_KITs" };

	public SPLine newBlockLine(TCComponentBOMLine bomLine)
	{
		try
		{
			TCComponent item = bomLine.getItem();
			TCComponentItemRevision itemIR = bomLine.getItemRevision();
			String uid = itemIR.getUid();
			String[] properties = bomLine.getProperties(blProps);
			boolean isDefault = properties[4].trim().isEmpty();
			SPLine resultBlockLine = emptyLine;

			if (item.getType().equals("Oc9_CompanyPart"))
			{
				resultBlockLine = new SPLineDetail();
				resultBlockLine.attributes.zone.setValue(properties[0]);
				resultBlockLine.attributes.position.setValue(properties[1]);
				resultBlockLine.isRenumerizable = properties[5].trim().equals("");
				resultBlockLine.setUid(uid);
				if (!properties[8].isEmpty())
				{
					sp.errorList.storeError(new Error(
							"������ � ��������������� " + item.getProperty("item_id") + " ����� ������ �� ��������."));
				}
				String typeOfPart = item.getProperty("oc9_TypeOfPart");
				if (typeOfPart.equals("��������� �������") || typeOfPart.equals("��������"))
				{
					/***********************
					 * ������ � ���������
					 ***********************/
					AIFComponentContext[] relatedDocs = bomLine.getItemRevision().getRelated("Oc9_DocRel");
					String itemID = bomLine.getItem().getProperty("item_id");
					String format = getFormat(itemID, relatedDocs);
					resultBlockLine.attributes.format.setValue(format);
					resultBlockLine.attributes.id.setValue(item.getProperty("item_id"));
					resultBlockLine.attributes.name.setValue(itemIR.getProperty("object_name"));
					resultBlockLine.attributes.quantity.setValue(properties[2]);
					resultBlockLine.attributes.remark.setValue(properties[3]);
					// resultBlockLine.addRefBOMLine(bomLine);
					if (typeOfPart.equals("��������� �������"))
					{
						resultBlockLine.blockContentType = EnumBlockType.ASSEMBLIES;
					}
					else
					{
						resultBlockLine.blockContentType = EnumBlockType.COMPLEXES;
					}
				}
				else if (typeOfPart.equals("������"))
				{
					/***************************** ������ *********************************/
					boolean hasDraft = false;
					AIFComponentContext[] relatedDocs = bomLine.getItemRevision().getRelated("Oc9_DocRel");
					String itemID = bomLine.getItem().getProperty("item_id");
					String format = getFormat(itemID, relatedDocs);
					if (!format.equals("��"))
					{
						hasDraft = true;
					}
					resultBlockLine.attributes.format.setValue(format);
					if (!hasDraft)
					{
						if (itemIR.getProperty("oc9_CADMaterial").equals(""))
						{
							sp.errorList.storeError(new Error("� ��-������ � ��������������� "
									+ item.getProperty("item_id") + " �� �������� ������� \"�������� ��������\""));
						}
						resultBlockLine.attributes.name.setValue(itemIR.getProperty("object_name") + "\n"
								+ itemIR.getProperty("oc9_CADMaterial") + " " + itemIR.getProperty("oc9_AddNote"));
					}
					else
					{
						resultBlockLine.attributes.name.setValue(itemIR.getProperty("object_name"));
					}
					resultBlockLine.attributes.id.setValue(item.getProperty("item_id"));
					resultBlockLine.attributes.quantity.setValue(properties[2]);
					if (hasDraft)
					{
						resultBlockLine.attributes.remark.setValue(properties[3]);
					}
					else
					{
						if (!itemIR.getProperty("oc9_mass").trim().equals(""))
						{
							resultBlockLine.attributes.remark.setValue(itemIR.getProperty("oc9_mass")
									+ " ��"/* + properties[3] */);
							resultBlockLine.attributes.remark.updateValueWith(properties[3]);
						}
						else
						{
							resultBlockLine.attributes.remark.setValue(properties[3]);
						}
					}
					// resultBlockLine.addRefBOMLine(bomLine);
					AIFComponentContext[] relatedBlanks = bomLine.getItemRevision().getRelated("Oc9_StockRel");
					if (relatedBlanks.length > 0)
					{
						SPLineDetail blank = new SPLineDetail();
						TCComponentItem blankItem = (TCComponentItem) relatedBlanks[0].getComponent();
						blank.attributes.position.setValue("-");
						blank.attributes.name.setValue(blankItem.getLatestItemRevision().getProperty("object_name")
								+ " " + "�������-��������� ��� " + resultBlockLine.attributes.id.getStringValue());
						if (!blankItem.getType().equals("CommercialPart"))
						{
							String blankItemID = blankItem.getProperty("item_id");
							relatedDocs = ((TCComponentItem) relatedBlanks[0].getComponent()).getLatestItemRevision()
									.getRelated("Oc9_DocRel");
							String blankFormat = getFormat(blankItemID, relatedDocs);
							if (!format.equals("��"))
							{
								blank.attributes.format.setValue(blankFormat);
							}
							blank.attributes.id.setValue(relatedBlanks[0].getComponent().getProperty("item_id"));
						}
						// resultBlockLine.getAttachedLines().add(blank);
					}
					resultBlockLine.blockContentType = EnumBlockType.DETAILS;
				}
				else if (typeOfPart.equals("��������"))
				{
					/**************************** ��������� ********************************/
					resultBlockLine.attributes.id.setValue(item.getProperty("item_id"));
					resultBlockLine.attributes.name.setValue(itemIR.getProperty("object_name"));
					resultBlockLine.attributes.quantity.setValue(properties[2]);
					// resultBlockLine.addRefBOMLine(bomLine);
					resultBlockLine.blockContentType = EnumBlockType.KITS;
				}
				else if (typeOfPart.equals(""))
				{
					sp.errorList.storeError(new Error("� ��������� � ������������ " + properties[2]
							+ "����������� �������� �������� \"��� �������\""));
				}
			}
			else if (item.getType().equals("CommercialPart"))
			{
				resultBlockLine = new SPLineMaterial();
				/**************************** ������������ ********************************/
				resultBlockLine.attributes.name.setValue(item.getProperty("oc9_RightName"));
				// if (item.getProperty("oc9_RightName").equals("������������ ��
				// �����������"))
				// {
				// resultBlockLine.addProperty("bNameNotApproved", "true");
				// }
				resultBlockLine.attributes.quantity.setValue(properties[2]);
				resultBlockLine.attributes.remark.setValue(properties[3]);
				// resultBlockLine.addRefBOMLine(bomLine);
				if (!properties[8].isEmpty())
				{
					((MaterialSpecLineAttributes)resultBlockLine.attributes).addKit(properties[8], properties[6],
							properties[2].isEmpty() ? 1 : Integer.parseInt(properties[2]));
				}
				if (item.getProperty("oc9_TypeOfPart").equals("������ �������"))
				{
					resultBlockLine.blockContentType = EnumBlockType.OTHERS;
				}
				else
				{
					resultBlockLine.blockContentType = EnumBlockType.STANDARDS;
				}
			}
			else if (item.getType().equals("Oc9_Material"))
			{
				/**************************** ��������� ********************************/
				resultBlockLine = new SPLineMaterial();
				String seCutLength = bomLine.getProperty("SE Cut Length");
				resultBlockLine.attributes.name.setValue(
						item.getProperty("oc9_RightName") + (seCutLength.isEmpty() ? "" : (" L=" + seCutLength)));
				// if (item.getProperty("oc9_RightName").equals("������������ ��
				// �����������"))
				// {
				// resultBlockLine.addProperty("bNameNotApproved", "true");
				// }
				resultBlockLine.attributes.quantity.setValue(properties[2]);
				// resultBlockLine.addRefBOMLine(bomLine);
				// resultBlockLine.addProperty("UOM", properties[7]);
				// resultBlockLine.addProperty("SE Cut Length", seCutLength);
				// resultBlockLine.addProperty("CleanName",
				// item.getProperty("oc9_RightName"));
				// resultBlockLine.addProperty("FromGeomMat", "");
				// resultBlockLine.addProperty("FromMat", "true");
				// resultBlockLine.addProperty("object_string",
				// bomLine.getProperty("object_string"));
				if (!seCutLength.isEmpty() && !properties[7].equals("*"))
				{
					sp.errorList.storeError(new Error("� ��������� � ��������������� " + item.getProperty("item_id")
							+ " ������� ��������� ������� �� ��."));
				}
				if (!properties[8].isEmpty())
				{
					((MaterialSpecLineAttributes)resultBlockLine.attributes).addKit(properties[8], properties[6],
							properties[2].isEmpty() ? 1 : Integer.parseInt(properties[2]));
				}
				resultBlockLine.blockContentType = EnumBlockType.MATERIALS;
			}
			else if (item.getType().equals("Oc9_GeomOfMat"))
			{
				/*************************
				 * ��������� ����������
				 ****************************/
				resultBlockLine = new SPLineDetail();
				AIFComponentContext[] materialBOMLines = bomLine.getChildren();
				if (materialBOMLines.length > 0)
				{
					if (materialBOMLines.length > 1)
					{
						sp.errorList.storeError(new Error("� ������� ��������� ��������� � ��������������� "
								+ item.getProperty("item_id") + " ������������ ����� ������ ���������."));
					}
					TCComponentItemRevision materialIR = ((TCComponentBOMLine) materialBOMLines[0].getComponent())
							.getItemRevision();
					String quantityMS = ((TCComponentBOMLine) materialBOMLines[0].getComponent())
							.getProperty("bl_quantity");
					BigDecimal quantityMD = new BigDecimal(quantityMS.equals("") ? "1" : quantityMS);
					BigDecimal quantotyGD = new BigDecimal(properties[2].equals("") ? "1" : properties[2]);
					String uom = ((TCComponentBOMLine) materialBOMLines[0].getComponent())
							.getProperty("bl_item_uom_tag");
					uom = uom.equals("*") ? "" : uom;
					resultBlockLine.attributes.name.setValue(materialIR.getItem().getProperty("oc9_RightName"));
//					if (materialIR.getItem().getProperty("oc9_RightName").equals("������������ �� �����������"))
//					{
//						resultBlockLine.addProperty("bNameNotApproved", "true");
//					}
					resultBlockLine.attributes.quantity.setValue(quantityMD.multiply(quantotyGD).toString());
					resultBlockLine.attributes.remark.setValue(uom);
					// resultBlockLine.addRefBOMLine(bomLine);
					// resultBlockLine.addProperty("SE Cut Length", "");
					// resultBlockLine.addProperty("CleanName",
					// materialIR.getItem().getProperty("oc9_RightName"));
					// resultBlockLine.addProperty("FromGeomMat", "true");
					// resultBlockLine.addProperty("FromMat", "");
					resultBlockLine.setUid(materialIR.getUid());
					resultBlockLine.blockContentType = EnumBlockType.MATERIALS;
				}
				else
				{
					sp.errorList.storeError(new Error("� ������� ��������� ��������� � ��������������� "
							+ item.getProperty("item_id") + " ����������� ��������."));
				}
			}
			return resultBlockLine;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public String getFormat(String itemID, AIFComponentContext[] relatedDocuments)
	{
		String format = "��";
		try
		{
			for (AIFComponentContext relatedDoc : relatedDocuments)
			{
				String docID = relatedDoc.getComponent().getProperty("item_id");
				String docFormat = ((TCComponentItem) relatedDoc.getComponent()).getLatestItemRevision()
						.getProperty("oc9_Format");
				if (docID.equals(itemID))
				{
					format = docFormat;
					break;
				}
				else if (itemID.contains("-"))
				{
					if (docID.equals(itemID.substring(0, itemID.lastIndexOf("-"))))
					{
						format = docFormat;
					}
					else if (docID.contains("-"))
					{
						if (docID.substring(0, docID.lastIndexOf("-"))
								.equals(itemID.substring(0, itemID.lastIndexOf("-"))))
						{
							format = docFormat;
						}
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return format;
	}

}
