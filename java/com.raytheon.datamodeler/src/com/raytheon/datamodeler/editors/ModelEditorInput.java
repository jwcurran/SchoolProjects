package com.raytheon.datamodeler.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import org.eclipse.jface.viewers.*;


import com.raytheon.datamodeler.classes.*;

public class ModelEditorInput implements IEditorInput{

	private TreeViewer treeViewer;
	private SeniorNode node;
	public int id;
	
	public ModelEditorInput(TreeViewer viewer){
		this.treeViewer = viewer;
		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		node = (SeniorNode) selection.getFirstElement();
		this.id = node.getID();
	}
	
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Name";		
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "Tooltip";
	}
	
	public TreeViewer getTree(){
		return this.treeViewer;
	}

	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
	
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        ModelEditorInput other = (ModelEditorInput) obj;
	        if (this.id != other.id)
	            return false;
	        return true;
	    }
	

}