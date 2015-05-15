package com.raytheon.datamodeler.views;

import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.raytheon.datamodeler.classes.*;

public class SaveBrowser{
	public SaveBrowser(SeniorNode node){
        Shell shell = new Shell ();
		FileDialog dialog = new FileDialog(shell, SWT.SAVE | SWT.MULTI);
        String [] filterNames = new String [] {"XML Files (.xml)"};
        String [] filterExtensions = new String [] {"*.xml"};
        String filterPath = "c:\\";
        dialog.setFilterNames (filterNames);
        dialog.setFilterExtensions (filterExtensions);
        dialog.setFilterPath (filterPath);
        dialog.setOverwrite(true);
        dialog.setFileName(node.getName() + ".xml");
        dialog.open();
        String[] selectedFileNames = dialog.getFileNames();
        if(selectedFileNames.length != 0)
        {
        	File file = new File(dialog.getFilterPath() + '\\' + selectedFileNames[0]);
        
        	try {
        		PrintWriter out = new PrintWriter(file);
        		out.println(node.getXMLString());
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        
        	shell.close();
	        shell.dispose();
        }
        else{
        	shell.close();
        	shell.dispose();
        }
	}
}