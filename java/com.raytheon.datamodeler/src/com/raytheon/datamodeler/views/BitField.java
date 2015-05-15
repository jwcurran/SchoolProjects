package com.raytheon.datamodeler.views;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.ScrolledComposite;

import com.raytheon.datamodeler.classes.SeniorMessage;
import com.raytheon.datamodeler.classes.SeniorModel;
import com.raytheon.datamodeler.classes.SeniorNode;
import com.raytheon.datamodeler.classes.SeniorField;

public class BitField extends ViewPart {

	public static final String ID = "com.raytheon.datamodeler.bitfield";
	
	private Composite parent;
	private Group messageGroup;
	private Group fieldGroup;
	private Group unusedGroup;
	//Needed multiple sizes of images because to render the same number of labels as there are bits was taking an extremely long time
	//for large messages. There might be a more elegant solution to pursue using canvases or the like.
	Image unusedImage = new Image(Display.getCurrent(), BitField.class.getResourceAsStream("unused2.jpg"));
	Image unused8Image = new Image(Display.getCurrent(), BitField.class.getResourceAsStream("unused8bit.jpg"));
	Image unused16Image = new Image(Display.getCurrent(), BitField.class.getResourceAsStream("unused16bit.jpg"));
	Image usedImage = new Image(Display.getCurrent(), BitField.class.getResourceAsStream("used2.jpg"));
	Image used8Image = new Image(Display.getCurrent(), BitField.class.getResourceAsStream("used8bit.jpg"));
	Image used16Image = new Image(Display.getCurrent(), BitField.class.getResourceAsStream("used16bit.jpg"));
	private SeniorModel model[];
	private IWorkbenchPage page;
	private TreeViewer treeViewer;
	
	public BitField() {
		
	}
	
	public void init(IViewSite site) throws PartInitException{
		super.init(site);
			
	}	

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		ScrolledComposite sc = new ScrolledComposite(parent.getParent(), SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL);
		parent.setParent(sc);
		sc.setContent(parent);
		this.parent = parent;
		
		
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
		
		getSite().getPage().addSelectionListener(listener);
	}
	
	ISelectionListener listener = new ISelectionListener() {
        public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        	TreeView treeView;;
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
           else if(messageGroup!=null)
        	   messageGroup.dispose();           
        }
     };
     
     
     public void dispose(){
    	 getSite().getPage().removeSelectionListener(listener);
     }

	public void refresh(SeniorMessage message){
		int i;
		int j;
		int start;
		int diff;
	
		if(messageGroup != null)
			messageGroup.dispose();
		
		messageGroup = new Group(parent,SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL);
		//messageGroup.setBounds(0, 0, 200, 50);
		messageGroup.setLayout(new RowLayout());
		messageGroup.setText(message.getName());
		
		for(i=0; i<message.getChildren().size();i++){
			SeniorField field = (SeniorField) message.getChildren().get(i);
			unusedGroup = new Group(messageGroup,SWT.MULTI|SWT.H_SCROLL);
			unusedGroup.setLayout(new RowLayout());
			unusedGroup.setVisible(false);
			
			fieldGroup = new Group(messageGroup,SWT.H_SCROLL);
			fieldGroup.setLayout(new RowLayout());
			fieldGroup.setText(field.getName() + ": " + field.getLength());
			//fieldGroup.setData(field);
			//fieldGroup.setFocus();

			
//Test implementation of menu for groups, would not work if clicked on a label within a group. Possible future feature 
//with menu added to labels within groups as well.
			
//			final Menu menu = new Menu(fieldGroup);
//			fieldGroup.setMenu(menu);
//			menu.addMenuListener(new MenuAdapter(){
//				 public void menuShown(MenuEvent e){
//					 MenuItem[] items = menu.getItems();
//			            for (int i = 0; i < items.length; i++)
//			            {
//			                items[i].dispose();
//			            }
//			            MenuItem item = new MenuItem(menu, SWT.PUSH);
//			            item.setText("Get Data");
//			            item.addListener(SWT.Selection, new Listener(){
//
//							@Override
//							public void handleEvent(Event event) {
//								SeniorNode node = (SeniorNode) fieldGroup.getData();
//								System.out.println()
//							}
//			            	
//			            });
//				 }
//			});
//						
			if(i==0)
				start = 0;
			else{
				SeniorField previous = (SeniorField) message.getChildren().get(i-1);
				if(previous.getAbsPos() != -1 && previous.getWordPos() != -1 && previous.getBitPos() !=-1)
					start = previous.getAbsPos() + previous.getLength();
				else
					start = 0;
			}
			
			diff = field.getAbsPos() - start;
			while(diff > 0){
				if(diff >= 16){
					Label label = new Label(unusedGroup, SWT.NONE);
					label.setImage(unused16Image);
					label.pack();
					diff -= 16;
				}
				else if(diff >= 8){
					Label label = new Label(unusedGroup, SWT.NONE);
					label.setImage(unused8Image);
					label.pack();
					diff -= 8;
				}
				else{
					Label label = new Label(unusedGroup, SWT.NONE);
					label.setImage(unusedImage);
					label.pack();
					diff--;
				}
			}
			if(unusedGroup.getChildren().length != 0)
				unusedGroup.setVisible(true);
			unusedGroup.setText(String.valueOf(field.getAbsPos() - start));
			unusedGroup.pack();
			messageGroup.pack();
			diff = field.getLength();
			while(diff > 0){
				if(diff >= 16){
					Label label = new Label(fieldGroup, SWT.NONE);
					label.setImage(used16Image);
					label.pack();
					diff -= 16;
				}
				else if(diff >= 8){
					Label label = new Label(fieldGroup, SWT.NONE);
					label.setImage(used8Image);
					label.pack();
					diff -= 8;
				}
				else{
					Label label = new Label(fieldGroup, SWT.NONE);
					label.setImage(usedImage);
					label.pack();
					diff--;
				}
			}
			fieldGroup.pack();
			messageGroup.pack();
		}
		messageGroup.pack();
		parent.pack();
        
	}
	
	@Override
	public void setFocus() {	
	}

}
