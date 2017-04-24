package vp.gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ErrorListDialog extends Dialog
{

	private Shell shell;
	private Display display;

	private ArrayList<String> errorList;

	public ErrorListDialog(Shell shell, ArrayList<String> errorList)
	{
		super(shell, SWT.APPLICATION_MODAL);
		this.errorList = errorList;
		open();
	}

	private void open()
	{
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContents()
	{

		shell = new Shell(getParent(), SWT.RESIZE | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(460, 260);
		shell.setText("Ошибка!");

		shell.setLayout(new GridLayout(1, false));

		Rectangle clientArea = shell.getClientArea();

		Label errLabel = new Label(shell, SWT.NONE);
		errLabel.setText("Обнаруженные ошибки:");
		errLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

		final Text text = new Text(shell, SWT.RESIZE | SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		text.setText(getErrorsAsString());
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		text.setEditable(false);
		text.setBackground(new Color(display, 255, 255, 255));

		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.setText("OK");
		btnOk.setLayoutData(new GridData(SWT.CENTER, SWT.END, false, false));
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				System.out.println("Cancel!");
				shell.close();
			}
		});
	}

	private String getErrorsAsString()
	{
		String errString = "";
		for (int i = 0; i < errorList.size(); i++) {
			errString += errorList.get(i) + "\n";
		}
		return errString.trim();
	}

}
