package com.raytheon.datamodeler.views;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import com.raytheon.datamodeler.classes.SeniorNode;


public class ModelTreeContentProvider extends ArrayContentProvider implements ITreeContentProvider{
	public Object[] getChildren(Object parentElement) {
		SeniorNode node = (SeniorNode) parentElement;
		return node.getChildren().toArray();
	}
	public Object getParent(Object element) {	
		//SeniorNode node = (SeniorNode) element;
		return null;
	}
	public boolean hasChildren(Object element) {
		SeniorNode node = (SeniorNode) element;
		return node.getChildren().size() > 0;
	}

}
