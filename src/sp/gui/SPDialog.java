package sp.gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.util.ListIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.teamcenter.rac.util.DateButton;

import reports.BlockContentType;
import sp.SP;
import sp.SPSettings;
import sp.spblock.SPBlockList;
import sp.spblock.SPBlock;
import util.DateUtil;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;

public class SPDialog extends Dialog
{
	private SP specification;
	
	protected Object result;
	protected Shell shell;
	private TabFolder tabFolder;
	private TabItem tabMain;
	private TabItem tabSignatures;
	private Composite compositeMain;
	private Composite compositeSignatures;

	private Table table;
	private Text text_AddedText;
	private Text text_PervPrim;
	private Text text_Litera1;
	private Text text_Litera2;
	private Text text_Litera3;
	private Text textDesigner;
	private Text textCheck;
	private Text textTCheck;
	private Text textNCheck;
	private Text textApprover;
	
	private DateButton dateDesigner;
	private DateButton dateCheck;
	private DateButton dateTCheck;
	private DateButton dateNCheck;
	private DateButton dateApprover;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SPDialog(Shell parent, int style, SP specification) {
		super(parent, style);
		this.specification = specification;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		fillContents();
		shell.setLayout(new FillLayout());
		//shlCgtwbabrfwbz.layout();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	
	/**
	 * Fill contents of the dialog
	 */

	private void fillContents(){
		SPBlockList blockList = (SPBlockList)specification.report.blockList;
		ListIterator<SPBlock> iterator = blockList.iterator();
		SPBlock block;
		TableItem blockItem;
		while(iterator.hasNext()){
			block = iterator.next();
			System.out.println("In fill contents: " + block.title + "=" + block.getLines().size());
			blockItem = new TableItem(table, SWT.NONE);
			if(!block.title.equals("Документация")){
				blockItem.setText(new String[]{block.title, String.valueOf(block.attributes.reservePosCount), String.valueOf(block.attributes.reserveLinesCount), String.valueOf(block.attributes.reservePosCount)});
			} else {
				blockItem.setText(new String[]{block.title, String.valueOf(block.attributes.reservePosCount), String.valueOf(block.attributes.reserveLinesCount), String.valueOf(block.attributes.reservePosCount)});
			}
		}
		text_AddedText.setText(SPSettings.additionalText==null?"":SPSettings.additionalText);
		
		text_Litera1.setText(specification.stampData.litera1==null?"":specification.stampData.litera1);
		text_Litera2.setText(specification.stampData.litera2==null?"":specification.stampData.litera2);
		text_Litera3.setText(specification.stampData.litera3==null?"":specification.stampData.litera3);
		text_PervPrim.setText(specification.stampData.pervPrim==null?"":specification.stampData.pervPrim);
		
		textDesigner.setText(specification.stampData.design==null?"":specification.stampData.design);
		textCheck.setText(specification.stampData.check==null?"":specification.stampData.check);
		textTCheck.setText(specification.stampData.techCheck==null?"":specification.stampData.techCheck);
		textNCheck.setText(specification.stampData.normCheck==null?"":specification.stampData.normCheck);
		textApprover.setText(specification.stampData.approve==null?"":specification.stampData.approve);

		//TODO okeanos
		String s_DesignDate = specification.stampData.designDate;
		String s_CheckDate = specification.stampData.checkDate;
		String s_TCheckDate = specification.stampData.techCheckDate;
		String s_NCheckDate = specification.stampData.normCheckDate;
		String s_ApproveDate = specification.stampData.approveDate;
		System.out.println("::DATE::"+s_DesignDate);
		if(s_DesignDate!=null) { dateDesigner.setDate(DateUtil.getDateFormSimpleString(s_DesignDate)); }else{ dateDesigner.setDate(""); }
		if(s_CheckDate!=null) { dateCheck.setDate(DateUtil.getDateFormSimpleString(s_CheckDate)); }else{ dateCheck.setDate(""); }
		if(s_TCheckDate!=null) { dateTCheck.setDate(DateUtil.getDateFormSimpleString(s_TCheckDate)); }else{ dateTCheck.setDate(""); }
		if(s_NCheckDate!=null) { dateNCheck.setDate(DateUtil.getDateFormSimpleString(s_NCheckDate)); }else{ dateNCheck.setDate(""); }
		if(s_ApproveDate!=null) { dateApprover.setDate(DateUtil.getDateFormSimpleString(s_ApproveDate)); }else{ dateApprover.setDate(""); }
	}
	
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell();
		shell.setSize(470, 605);
		shell.setText("\u0421\u043F\u0435\u0446\u0438\u0444\u0438\u043A\u0430\u0446\u0438\u044F");
		tabFolder = new TabFolder(shell, SWT.NONE);
		tabMain = new TabItem(tabFolder, SWT.BORDER);
		tabSignatures = new TabItem(tabFolder, SWT.BORDER);
		compositeMain = new Composite(tabFolder, SWT.NONE);
		compositeSignatures = new Composite(tabFolder, SWT.NONE);
		compositeMain.setLayout(null);
		compositeSignatures.setLayout(null);
		tabMain.setControl(compositeMain);
		tabSignatures.setControl(compositeSignatures);
		
		Label labelDesigner = new Label(compositeSignatures, SWT.NONE);
		labelDesigner.setText("\u0420\u0430\u0437\u0440\u0430\u0431\u043E\u0442\u0430\u043B");
		labelDesigner.setBounds(37, 47, 90, 23);
		
		textDesigner = new Text(compositeSignatures, SWT.BORDER);
		textDesigner.setBounds(144, 44, 110, 23);
		
		Label labelCheck = new Label(compositeSignatures, SWT.NONE);
		labelCheck.setBounds(37, 88, 90, 23);
		labelCheck.setText("\u041F\u0440\u043E\u0432\u0435\u0440\u0438\u043B");
		
		textCheck = new Text(compositeSignatures, SWT.BORDER);
		textCheck.setBounds(144, 85, 110, 23);
		
		/*Label labelAddCheck = new Label(compositeSignatures, SWT.NONE);
		labelAddCheck.setBounds(173, 20, 90, 23);
		labelAddCheck.setText("\u0424\u0430\u043C\u0438\u043B\u0438\u044F");*/
		
		textTCheck = new Text(compositeSignatures, SWT.BORDER);
		textTCheck.setBounds(144, 128, 110, 23);
		
		Label labelNCheck = new Label(compositeSignatures, SWT.NONE);
		labelNCheck.setBounds(37, 173, 90, 23);
		labelNCheck.setText("\u041D.\u043A\u043E\u043D\u0442\u0440\u043E\u043B\u044C");
		
		Label labelTCheck = new Label(compositeSignatures, SWT.NONE);
		labelTCheck.setBounds(37, 128, 90, 23);
		labelTCheck.setText("Т.контр");
		
		Label labelApprover = new Label(compositeSignatures, SWT.NONE);
		labelApprover.setBounds(37, 215, 90, 23);
		labelApprover.setText("\u0423\u0442\u0432\u0435\u0440\u0434\u0438\u043B");
		
		textNCheck = new Text(compositeSignatures, SWT.BORDER);
		textNCheck.setBounds(144, 170, 110, 23);
		
		textApprover = new Text(compositeSignatures, SWT.BORDER);
		textApprover.setBounds(144, 212, 110, 23);
		
		//TODO okeanos
		Composite compositeDesigner = new Composite(compositeSignatures, SWT.EMBEDDED);
		compositeDesigner.setBounds(260, 44, 150, 23);
		java.awt.Frame frameDesigner = SWT_AWT.new_Frame(compositeDesigner);
		java.awt.Panel panelDesigner = new java.awt.Panel(new java.awt.BorderLayout());
	    frameDesigner.add(panelDesigner);
		dateDesigner = new DateButton();
		dateDesigner.setDoubleBuffered(true);
		panelDesigner.add(dateDesigner);
		
		Composite compositeCheck = new Composite(compositeSignatures, SWT.EMBEDDED);
		compositeCheck.setBounds(260, 85, 150, 23);
		java.awt.Frame frameCheck = SWT_AWT.new_Frame(compositeCheck);
	    java.awt.Panel panelCheck = new java.awt.Panel(new java.awt.BorderLayout());
	    frameCheck.add(panelCheck);
		dateCheck = new DateButton();
		dateCheck.setDoubleBuffered(true);
		panelCheck.add(dateCheck);
		
		Composite compositeTCheck = new Composite(compositeSignatures, SWT.EMBEDDED);
		compositeTCheck.setBounds(260, 128, 150, 23);
		java.awt.Frame frameTCheck = SWT_AWT.new_Frame(compositeTCheck);
	    java.awt.Panel panelTCheck = new java.awt.Panel(new java.awt.BorderLayout());
	    frameTCheck.add(panelTCheck);
		dateTCheck = new DateButton();
		dateTCheck.setDoubleBuffered(true);
		panelTCheck.add(dateTCheck);
		
		Composite compositeNCheck = new Composite(compositeSignatures, SWT.EMBEDDED);
		compositeNCheck.setBounds(260, 170, 150, 23);
		java.awt.Frame frameNCheck = SWT_AWT.new_Frame(compositeNCheck);
		java.awt.Panel panelNCheck = new java.awt.Panel(new java.awt.BorderLayout());
		frameNCheck.add(panelNCheck);
		dateNCheck = new DateButton();
		dateNCheck.setDoubleBuffered(true);
		panelNCheck.add(dateNCheck);
		
		Composite compositeApprover = new Composite(compositeSignatures, SWT.EMBEDDED);
		compositeApprover.setBounds(260, 212, 150, 23);
		java.awt.Frame frameApprover = SWT_AWT.new_Frame(compositeApprover);
	    java.awt.Panel panelApprover = new java.awt.Panel(new java.awt.BorderLayout());
	    frameApprover.add(panelApprover);
		dateApprover = new DateButton();
		dateApprover.setDoubleBuffered(true);
		panelApprover.add(dateApprover);
		
	    tabMain.setText("Настройки");
	    tabSignatures.setText("\u041F\u043E\u0434\u043F\u0438\u0441\u0430\u043D\u0442\u044B");

		
		final Button button_Renumerize = new Button(compositeMain, SWT.CHECK);
		button_Renumerize.setBounds(10, 10, 213, 16);
		button_Renumerize.setText("\u041F\u0435\u0440\u0435\u043D\u0443\u043C\u0435\u0440\u043E\u0432\u0430\u0442\u044C \u043F\u043E\u0437\u0438\u0446\u0438\u0438");
		button_Renumerize.setEnabled(SPSettings.canRenumerize);
		if(!button_Renumerize.isEnabled()) {
			button_Renumerize.setToolTipText("Недоступно, указан запрет смены позиций.");
		} else {
			button_Renumerize.setToolTipText("Перенумерация позиций с возможностью резерва строк и позиций.");
		}
		
		Group group = new Group(compositeMain, SWT.NONE);
		group.setText("\u0420\u0435\u0437\u0435\u0440\u0432 \u043F\u043E\u0437\u0438\u0446\u0438\u0439 \u043F\u043E \u0440\u0430\u0437\u0434\u0435\u043B\u0430\u043C");
		group.setBounds(10, 32, 424, 220);
		
		table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setBounds(10, 21, 404, 189);
		table.setEnabled(false);
		
		//TODO implement multiline?
		/*Listener paintListener = new Listener() {
		      public void handleEvent(Event event) {
		        switch (event.type) {
		        case SWT.MeasureItem: {
		          TableItem item = (TableItem) event.item;
		          String text = getText(item, event.index);
		          Point size = event.gc.textExtent(text);
		          if(text.startsWith("Устана")){
		        	  event.width = size.x*table.getColumnCount();
		          }else {
		        	  event.width = size.x;
		          }
		          event.height = Math.max(event.height, size.y);
		          break;
		        }
		        case SWT.PaintItem: {
		          TableItem item = (TableItem) event.item;
		          String text = getText(item, event.index);
		          Point size = event.gc.textExtent(text);
		          int offset2 = event.index == 0 ? Math.max(0, (event.height - size.y) / 2) : 0;
		          event.gc.drawText(text, event.x, event.y + offset2, true);
		          break;
		        }
		        case SWT.EraseItem: {
		          event.detail &= ~SWT.FOREGROUND;
		          break;
		        }
		        }
		      }

		      String getText(TableItem item, int column) {
		        String text = item.getText(column);
		        return text;
		      }
		};
		table.addListener(SWT.MeasureItem, paintListener);
		table.addListener(SWT.PaintItem, paintListener);
		table.addListener(SWT.EraseItem, paintListener);*/
		
		final TableEditor editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.RIGHT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		
		table.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					boolean visible = false;
					final TableItem item = table.getItem(index);
					if(!BlockContentType.existsBlockWithTitle(item.getText(0))) {
						index++;
						continue;
					};
					for (int i = 1; i < table.getColumnCount(); i++) {
						if((item.getText(0).equals("Документация") || item.getText(0).equals("Комплекты")) && i!=2){ continue; };
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final Text text = new Text(table, SWT.NONE);
							Listener textListener = new Listener() {
								@Override
								public void handleEvent(final Event e) {
									switch (e.type) {
									case SWT.FocusOut:
										item.setText(column, text.getText());
										text.dispose();
										break;
									case SWT.Traverse:
										switch (e.detail) {
										case SWT.TRAVERSE_RETURN:
											item.setText(column, text.getText());
											// FALL THROUGH
										case SWT.TRAVERSE_ESCAPE:
											text.dispose();
											e.doit = false;
										}
										break;
									}
								}
							};
							text.addListener(SWT.FocusOut, textListener);
							text.addListener(SWT.Traverse, textListener);
							text.addListener(SWT.Verify, new Listener() {
							      public void handleEvent(Event e) {
							        String string = e.text;
							        char[] chars = new char[string.length()];
							        string.getChars(0, chars.length, chars, 0);
							        for (int i = 0; i < chars.length; i++) {
							          if (!('0' <= chars[i] && chars[i] <= '9')) {
							            e.doit = false;
							            return;
							          }
							        }
							      }
							});
							editor.setEditor(text, item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							return;
						}
						if (!visible && rect.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible)
						return;
					index++;
				}
			}
		});
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(149);
		tableColumn.setText("\u0420\u0430\u0437\u0434\u0435\u043B");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(74);
		tableColumn_1.setText("\u0420\u0435\u0437\u0435\u0440\u0432 \u043F\u043E\u0437.");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(82);
		tableColumn_2.setText("\u0420\u0435\u0437\u0435\u0440\u0432 \u0441\u0442\u0440\u043E\u043A");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(93);
		tableColumn_3.setText("\u0418\u043D\u0442\u0435\u0440\u0432\u0430\u043B \u043F\u043E\u0437.");
		
		final Button button_ReadLastRevPos = new Button(compositeMain, SWT.CHECK);
		button_ReadLastRevPos.setText("\u0417\u0430\u0447\u0438\u0442\u0430\u0442\u044C \u043F\u043E\u0437\u0438\u0446\u0438\u0438 \u0441 \u043F\u0440\u043E\u0448\u043B\u043E\u0439 \u0440\u0435\u0432\u0438\u0437\u0438\u0438");
		button_ReadLastRevPos.setBounds(10, 258, 225, 16);
		button_ReadLastRevPos.setEnabled(SPSettings.canReadPrevRevPositions);
		button_ReadLastRevPos.setVisible(true);
		
		final Button button_UseReservePos = new Button(compositeMain, SWT.CHECK);
		button_UseReservePos.setText("\u0418\u0441\u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u044C \u0440\u0435\u0437\u0435\u0440\u0432 \u043F\u043E\u0437\u0438\u0446\u0438\u0439");
		button_UseReservePos.setBounds(10, 282, 225, 16);
		button_UseReservePos.setEnabled(SPSettings.canUseReservePositions);
		button_UseReservePos.setVisible(true);
		
		final Button button_ShowAdditionalForm = new Button(compositeMain, SWT.CHECK);
		button_ShowAdditionalForm.setBounds(10, 320, 225, 16);
		button_ShowAdditionalForm.setText("Показать дополнительную форму");
		
		Label label = new Label(compositeMain, SWT.NONE);
		label.setBounds(10, 317, 136, 13);
		label.setText("\u0414\u043E\u043F\u043E\u043B\u043D\u0438\u0442\u0435\u043B\u044C\u043D\u044B\u0439 \u0442\u0435\u043A\u0441\u0442");
		
		text_AddedText = new Text(compositeMain, SWT.BORDER | SWT.V_SCROLL);
		text_AddedText.setBounds(10, 336, 424, 78);
		
		text_PervPrim = new Text(compositeMain, SWT.BORDER);
		text_PervPrim.setBounds(10, 437, 154, 19);
		
		Label label_1 = new Label(compositeMain, SWT.NONE);
		label_1.setText("\u041F\u0435\u0440\u0432\u0438\u0447\u043D\u0430\u044F \u043F\u0440\u0438\u043C\u0435\u043D\u044F\u0435\u043C\u043E\u0441\u0442\u044C");
		label_1.setBounds(10, 420, 160, 13);
		
		Label label_litera_1 = new Label(compositeMain, SWT.NONE);
		label_litera_1.setText("\u041B\u0438\u0442\u0435\u0440\u0430 1");
		label_litera_1.setBounds(10, 460, 76, 13);
		
		text_Litera1 = new Text(compositeMain, SWT.BORDER);
		text_Litera1.setBounds(10, 477, 76, 19);
		
		Label label_litera_2 = new Label(compositeMain, SWT.NONE);
		label_litera_2.setText("\u041B\u0438\u0442\u0435\u0440\u0430 2");
		label_litera_2.setBounds(96, 460, 76, 13);
		
		text_Litera2 = new Text(compositeMain, SWT.BORDER);
		text_Litera2.setBounds(96, 477, 76, 19);
		
		Label label_litera_3 = new Label(compositeMain, SWT.NONE);
		label_litera_3.setText("\u041B\u0438\u0442\u0435\u0440\u0430 3");
		label_litera_3.setBounds(182, 460, 76, 13);
		
		text_Litera3 = new Text(compositeMain, SWT.BORDER);
		text_Litera3.setBounds(182, 477, 76, 19);
		
		Button btnOk = new Button(compositeMain, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SPSettings.isOKPressed = true;
				SPSettings.doRenumerize = button_Renumerize.getSelection();
				SPSettings.doReadPrevRevPositions = button_ReadLastRevPos.getSelection();
				SPSettings.doUseReservePositions = button_UseReservePos.getSelection();
				SPSettings.doShowAdditionalForm = button_ShowAdditionalForm.getSelection();
				SPSettings.additionalText = text_AddedText.getText();
				
				specification.stampData.litera1 = text_Litera1.getText();
				specification.stampData.litera2 = text_Litera2.getText();
				specification.stampData.litera3 = text_Litera3.getText();
				specification.stampData.pervPrim = text_PervPrim.getText();
				
				specification.stampData.design = textDesigner.getText();
				specification.stampData.check= textCheck.getText();
				specification.stampData.techCheck = textTCheck.getText();
				specification.stampData.normCheck = textNCheck.getText();
				specification.stampData.approve = textApprover.getText();
				
				specification.stampData.designDate = dateDesigner.getText().equals("Дата не установлена.")?null:fixData(dateDesigner.getText());
				specification.stampData.checkDate = dateCheck.getText().equals("Дата не установлена.")?null:fixData(dateCheck.getText());
				specification.stampData.techCheckDate = dateTCheck.getText().equals("Дата не установлена.")?null:fixData(dateTCheck.getText());
				specification.stampData.normCheckDate = dateNCheck.getText().equals("Дата не установлена.")?null:fixData(dateNCheck.getText());
				specification.stampData.approveDate = dateApprover.getText().equals("Дата не установлена.")?null:fixData(dateApprover.getText());
				
				SPBlockList blockList = (SPBlockList)specification.report.blockList;
				int j = 0;
				for(int i = 0; i < table.getItemCount(); i++){
					TableItem tableItem = table.getItem(i);
					if(tableItem.getText(0).startsWith("Устанавливается")) continue;
					SPBlock block = (SPBlock) blockList.get(j);
					block.attributes.reservePosCount = Integer.parseInt(tableItem.getText(1).isEmpty()?"0":tableItem.getText(1));
					block.attributes.reserveLinesCount = Integer.parseInt(tableItem.getText(2).isEmpty()?"0":tableItem.getText(2));
					block.attributes.intervalPosCount = Integer.parseInt(tableItem.getText(3).isEmpty()?"0":tableItem.getText(3));
					j++;
				}
				/*ListIterator<Block> iterator = blockList.listIterator();
				Block block;
				TableItem blockItem;
				while(iterator.hasNext()){
					block = iterator.next();
					System.out.println("LOL+"+block.getReservePosNum());
					blockItem = new TableItem(table, SWT.NONE);
					if(!block.getBlockTitle().equals("Документация")){
						blockItem.setText(new String[]{block.getBlockTitle(), String.valueOf(block.getReservePosNum()), String.valueOf(block.getReserveLinesNum()), String.valueOf(block.getIntervalPosNum())});
					} else {
						blockItem.setText(new String[]{block.getBlockTitle(), String.valueOf(block.getReservePosNum()), String.valueOf(block.getReserveLinesNum()), String.valueOf(block.getIntervalPosNum())});
					}
					if(block.getBlockType().equals("Default") && iterator.nextIndex()!=blockList.size()){
						if(blockList.get(iterator.nextIndex()).getBlockType().equals("ME")){
							blockItem = new TableItem(table, SWT.NONE);
							blockItem.setText(new String[]{"Устанавливается по " + Specification.settings.getStringProperty("MEDocumentId")});
							blockItem.setGrayed(true);
						}
					}
				}*/
				
				shell.dispose();
				System.out.println("OK!");
			}
		});
		
		btnOk.setBounds(139, 512, 68, 23);
		btnOk.setText("OK");
		
		button_Renumerize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.setEnabled(button_Renumerize.getSelection());
			}
		});
		
		Button btnCancel = new Button(compositeMain, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnCancel.setText("Cancel");
		btnCancel.setBounds(229, 512, 68, 23);
	}
	
	private String fixData(String input){
		String output = input;
		if(input.substring(0, input.indexOf("-")).length()<2){
			output = "0"+output;
		}
		return output;
	}
}
