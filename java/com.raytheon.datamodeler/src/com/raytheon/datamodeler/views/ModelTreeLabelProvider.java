package com.raytheon.datamodeler.views;

import org.eclipse.swt.graphics.*;
import org.eclipse.jface.viewers.LabelProvider;
import com.raytheon.datamodeler.classes.SeniorNode;

public class ModelTreeLabelProvider extends LabelProvider{
	private String displayName;
	
	public Image getImage(Object element) {
		return null;
	}
	
	public String getText(Object element) {
		SeniorNode node = (SeniorNode) element;
		
		switch(node.getClass().getSimpleName()){
			case "SeniorModel": displayName = "<" + node.getName() + "> Model";
								break;
			case "SeniorDomain": displayName = "<" + node.getName() + "> Domain";
								break;
			case "SeniorSystem": displayName = "<" + node.getName() + "> System";
								break;
			case "SeniorSubsystem": displayName = "<" + node.getName() + "> Subsystem";
								break;
			case "SeniorMessage": displayName = "<" + node.getName() + "> Message";
								break;
			case "SeniorField": displayName = "<" + node.getName() + "> Field";
								break;
			
			default: displayName = "Node";					
		}
		return displayName;
	}

}
