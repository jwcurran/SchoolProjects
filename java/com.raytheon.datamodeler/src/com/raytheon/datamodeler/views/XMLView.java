package com.raytheon.datamodeler.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.*;

import com.raytheon.datamodeler.classes.SeniorField;
import com.raytheon.datamodeler.classes.SeniorMessage;
import com.raytheon.datamodeler.classes.SeniorModel;
import com.raytheon.datamodeler.classes.SeniorNode;

public class XMLView extends ViewPart {

	public static final String ID = "com.raytheon.datamodeler.xmlview";
	
	private TreeViewer treeViewer;
	private Composite parent;
	private Text label;
	private SeniorModel model[];

	private IWorkbenchPage page;
	
	public XMLView() {

	}
	
	ISelectionListener listener = new ISelectionListener() {
        public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        	TreeView treeView;
        	if(part instanceof TreeView){
        		 treeView = (TreeView) part;
        	}
        	else{
        		return;
        	}
        	treeViewer = treeView.getTree();
        	model = (SeniorModel[])treeViewer.getInput(); 	
           if (!(selection instanceof IStructuredSelection))
              return;
           IStructuredSelection sel = (IStructuredSelection) selection;
           SeniorNode node = (SeniorNode) sel.getFirstElement();
           if(node instanceof SeniorMessage)
        	   refresh((SeniorMessage)node);
           else if(node instanceof SeniorField){
        	   SeniorMessage message = (SeniorMessage) model[0].getParentNode((SeniorField) node);
        	   refresh(message);
           }
           else if(label !=null)
        	   label.dispose();           
        }
     };

	public void init(IViewSite site) throws PartInitException{
		super.init(site);
	}
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		//label = new Text(this.parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL|SWT.READ_ONLY);
		getSite().getPage().addSelectionListener(listener);
		
//		IWorkbench bench = PlatformUI.getWorkbench();
//		IWorkbenchWindow window = bench.getActiveWorkbenchWindow();
//		page = window.getActivePage();
//		try {
//			TreeView tree = (TreeView) page.showView(TreeView.ID);
//			this.treeViewer = tree.getTree();
//			
//			} catch (PartInitException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		model = (SeniorModel[])treeViewer.getInput();
	}

	public void refresh(SeniorMessage message){		
		IWorkbench bench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = bench.getActiveWorkbenchWindow();
		page = window.getActivePage();
		try {
			TreeView tree = (TreeView) page.showView(TreeView.ID);
			this.treeViewer = tree.getTree();
			
			} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model = (SeniorModel[])treeViewer.getInput();
		
		if(label != null)
			label.dispose();
		
		label = new Text(this.parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL|SWT.READ_ONLY);
		label.setText(message.getXMLString());
		label.pack();		
		parent.pack();
	}
	
	public void dispose(){
		getSite().getPage().removeSelectionListener(listener);
	}
	
	@Override
	public void setFocus() {

	}

}
