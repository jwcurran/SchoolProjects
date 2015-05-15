package com.raytheon.datamodeler.views;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Popup implements Runnable{
	String message;
	public Popup(String errorString){
		message = errorString;
	}
//	String message;
//	
//	public Popup(String errorString){
//		message = errorString;
//	}
//	
//	public void run(){
//		final Display display = new Display ();
//		Shell shell = new Shell (display);
//		shell.setSize(400,300);
//		RowLayout layout = new RowLayout();
//		RowData data;
//		shell.setLayout(layout);
//		shell.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
//		shell.setText("error");
//		
//		Image errorImage = new Image(Display.getCurrent(), BitField.class.getResourceAsStream("error2.jpg"));
//		
//		data = new RowData();
//		data.width = 400;
//		Label error = new Label(shell, SWT.CENTER);
//		error.setImage(errorImage);
//		error.setLayoutData(data);
//		error.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
//		error.pack();
//		
//		data = new RowData();
//		data.width = 379;
//		data.height = 100;
//		Label label = new Label(shell, SWT.CENTER);
//		label.setText(message + " error!");
//		//label.setText("Bit Collision!....Reposition Field.");
//		FontData[] fD = label.getFont().getFontData();
//		fD[0].setHeight(14);
//		fD[0].setStyle(SWT.BOLD);
//		label.setFont( new Font(display,fD[0]));
//		label.setLayoutData(data);
//		label.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
//		label.pack();
//		
//		data = new RowData();
//		data.height = 50;
//		data.width = 379;
//		Button ok = new Button (shell, SWT.PUSH);
//		ok.setText ("OK");
//		ok.setAlignment(SWT.CENTER);
//		ok.setLayoutData(data);
//		
//		ok.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				display.dispose();
//			}
//		});
//		ok.pack();
//		
//		
//		shell.open ();
//		while (!shell.isDisposed ()) {
//			if (!display.readAndDispatch ()) display.sleep ();
//		}
//		display.dispose ();
//	}
//	
//	 public static void main(String args[]) {
//	        (new Popup(new String())).start();
//	    }

@Override
	public void run() {
		// TODO Auto-generated method stub
		final JFrame frame = new JFrame("ERROR!");
		frame.setSize(600, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setBackground(Color.GRAY);
		frame.setVisible(true);
		

		
		java.awt.Font font = new Font("Impact", java.awt.Font.BOLD, 22);
		
		JLabel errorText = new JLabel();
		errorText.setVerticalAlignment(JLabel.CENTER);
		errorText.setHorizontalAlignment(JLabel.CENTER);
		errorText.setFont(font);
		errorText.setText(message);
		errorText.setBounds(0, 50, 600, 50);
		errorText.setVisible(true);
		frame.add(errorText);
		
		
		
		java.awt.Font font2 = new Font("Impact", java.awt.Font.BOLD, 20);
		JButton ok = new JButton();
		ok.setText("OK");
		ok.setFont(font2);
		ok.setBounds(50,160,500,100);
		ok.setVerticalAlignment(JButton.CENTER);
		ok.setHorizontalAlignment(JButton.CENTER);
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				
			}
			
		});
		frame.add(ok);
		ok.setVisible(true);
		
		
//		ImageIcon icon = new ImageIcon("src/com/raytheon/datamodeler/views/error2.jpg");
//		JLabel errorImage = new JLabel(icon);
//		errorImage.setBounds(250,0,50,50);
//		
//		frame.add(errorImage);
//		errorImage.setVisible(true);
		
		
	}
	public static void main(String args[]) {
	    (new Popup(new String())).run();
	}

}
