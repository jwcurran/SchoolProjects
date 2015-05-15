package com.raytheon.datamodeler.perspective;

import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PerspectiveAdapter;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchEditorsHandler;

import com.raytheon.datamodeler.editors.ModelEditorInput;
import com.raytheon.datamodeler.views.BitField;
import com.raytheon.datamodeler.views.TreeView;
import com.raytheon.datamodeler.views.XMLView;

public class PerspectiveFactory implements IPerspectiveFactory{
	TreeView tree = new TreeView();

	@Override
	public void createInitialLayout(IPageLayout layout) {
		// TODO Auto-generated method stub
		
		layout.setEditorAreaVisible(true);
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.25f, layout.getEditorArea());
		left.addView(TreeView.ID);
		left.addView(XMLView.ID);		
		//layout.addView(TreeView.ID, IPageLayout.LEFT, 0.30f, layout.getEditorArea());
		
		layout.addView(BitField.ID, IPageLayout.BOTTOM, 0.75f, layout.ID_OUTLINE);
		
		layout.setFixed(false);
		
	}

}

