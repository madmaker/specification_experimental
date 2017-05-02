package sp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.teamcenter.rac.kernel.TCPreferenceService;

import reports.BlockContentType;

public class DocumentTypes
{
	private final String			DocTypesTCPropName	= "Oc9_Spec_DocumentTypesPriority";
	private final String			KitTypesTCPropName	= "Oc9_Spec_DocumentComplexTypesPriority";
	private ArrayList<DocumentType>	types;

	public DocumentTypes()
	{
		types = new ArrayList<DocumentType>();
		//loadValuesFormFile(new File(this.getClass().getClassLoader().getResource("doc_types.txt").getFile()),
		//		BlockContentType.DOCUMENTS);
		loadValuesFromTCPref(DocTypesTCPropName, BlockContentType.DOCUMENTS);
		loadValuesFromTCPref(KitTypesTCPropName, BlockContentType.KITS);
	}

	public void setDocumentTypes(ArrayList<DocumentType> types)
	{
		this.types = types;
	}

	public void loadValuesFromTCPref(String preferenceName, int targetBlockType)
	{
		String shortName;
		String longName;
		TCPreferenceService ps = SPHandler.session.getPreferenceService();
		String[] docTypes = ps.getStringArray(ps.TC_preference_site, preferenceName);
		for (String docType : docTypes)
		{
			int posOfFirstSpace = docType.indexOf(" ");
			if (posOfFirstSpace != -1)
			{
				shortName = docType.substring(0, posOfFirstSpace);
				longName = docType.substring(posOfFirstSpace + 1, docType.length());
				types.add(new DocumentType(shortName, longName, targetBlockType));
			}
		}
	}

	private void loadValuesFormFile(File file, int targetBlockType)
	{
		String shortName;
		String longName;
		int spaceIndex = -1;
		if (file != null)
		{
			try (Scanner scanner = new Scanner(file))
			{
				while (scanner.hasNextLine())
				{
					String line = scanner.nextLine();
					spaceIndex = line.indexOf(" ");
					if (spaceIndex == -1)
					{
						continue;
					}
					shortName = line.substring(0, line.indexOf(" ")).trim();
					longName = line.substring(line.indexOf(" ") + 1).trim();
					types.add(new DocumentType(shortName, longName, targetBlockType));
				}
				scanner.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private String getType(String input)
	{
		String result = null;
		String symbolPart = input.replaceAll("[^А-Яа-я]+", "");

		for (DocumentType type : types)
		{
			if (type.shortName.equals(input) && type.shortName.length() == input.length())
			{
				result = type.longName;
				break;
			}
			else if (type.equals(symbolPart) && type.shortName.length() != input.length())
			{
				result = type.longName;
			}
			else if (type.equals(symbolPart) && type.shortName.length() == input.length())
			{
				result = type.longName;
				break;
			}
		}

		return result;
	}
}
