package com.raytheon.datamodeler.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class ImportBrowser{
	private String selectedFile;
	
	public ImportBrowser(){
		Shell shell = new Shell ();
		FileDialog dialog = new FileDialog(shell, SWT.OPEN | SWT.MULTI);
        String [] filterNames = new String [] {"XML Files (.xml)"};
        String [] filterExtensions = new String [] {"*.xml"};
        String filterPath = "c:\\";
        dialog.setFilterNames (filterNames);
        dialog.setFilterExtensions (filterExtensions);
        dialog.setFilterPath (filterPath);
        dialog.setOverwrite(true);
        dialog.open();
        String[] selectedFileNames = dialog.getFileNames();
        if(selectedFileNames.length != 0)
        	selectedFile = dialog.getFilterPath() + '\\' + selectedFileNames[0];
        else
        	selectedFile = null;
        
        shell.close();
        shell.dispose();
	}
	
	public String getSelectedFile(){
		return selectedFile;
	}
}
