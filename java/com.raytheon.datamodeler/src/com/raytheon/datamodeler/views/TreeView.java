package com.raytheon.datamodeler.views;



import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;

import com.raytheon.datamodeler.classes.*;
import com.raytheon.datamodeler.editors.*;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Action;

public class TreeView extends ViewPart {
	
	public static final String ID = "com.raytheon.datamodeler.treeview";
	
	public TreeViewer treeViewer;
	private SeniorModel[] model;
	private Button newButton;
	private Button importButton;
	private Button exportButton;
	private Button discardButton;
	//private Button editButton;
	private ButtonHandler handler;
	private GridLayout layout;
	private IWorkbench wb = PlatformUI.getWorkbench();
	private IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	private IWorkbenchPage page = win.getActivePage();


	public TreeView() {
			
	}

	
	//************************************************************** [ Button handler for button events ]******************************
	class ButtonHandler implements SelectionListener{
			
		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.widget == newButton){
				//importButton.setEnabled(false);
				//editButton.setEnabled(true);
				//exportButton.setEnabled(true);
				discardButton.setEnabled(true);
				newButton.setEnabled(false);
								
				model[0] = new SeniorModel();
				model[0].addChild(new SeniorDomain());
				model[0].getChildren().get(0).addChild(new SeniorSystem());
				model[0].getChildren().get(0).getChildren().get(0).addChild(new SeniorSubsystem());
				model[0].getChildren().get(0).getChildren().get(0).getChildren().get(0).addChild(new SeniorMessage());
				model[0].getChildren().get(0).getChildren().get(0).getChildren().get(0).getChildren().get(0).addChild(new SeniorField());
				treeViewer.setInput(model);
				treeViewer.expandAll();
			}
			
			else if(e.widget == discardButton){
				importButton.setEnabled(false);
				exportButton.setEnabled(false);
				discardButton.setEnabled(false);
				newButton.setEnabled(true);
				//editButton.setEnabled(false);
				closeEditors(model[0]);
				treeViewer.remove(treeViewer.getInput());
				model[0] = null;
			}
			
			else if(e.widget == exportButton){
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				closeEditors((SeniorNode)selection.getFirstElement());
				SaveBrowser sb = new SaveBrowser((SeniorNode)selection.getFirstElement());
			}
			
			else if(e.widget == importButton){
				final ImportBrowser ib = new ImportBrowser();
		    	IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		    	SeniorNode node = (SeniorNode) selection.getFirstElement();
		    	String file = ib.getSelectedFile();
		    	if(file!=null){
			    	SeniorNode fromxml = SeniorNode.getObjectFromXML(ib.getSelectedFile());
			    	if(node.getChildType().equals(fromxml.getClass().getSimpleName())){
			    		node.addChild(fromxml);
			    	}
			    	else{
			    		Popup pop = new Popup(fromxml.getName() + " import: wrong parent type");
			    		pop.run();
			    	}
			    	
			        treeViewer.refresh();
			    }							
			}
//			else if(e.widget == editButton){
//				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
//		    	SeniorNode node = (SeniorNode) selection.getFirstElement();
//		    	switch(node.getClass().getSimpleName()){
//		    	case "SeniorModel":
//		    		try {
//						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), ModelEditor.ID);
//					} 
//					catch (PartInitException e1) {
//						e1.printStackTrace();
//					}
//		    		break;
//		    	case "SeniorDomain": 
//		    		try {
//						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), DomainEditor.ID);
//					} 
//					catch (PartInitException e1) {
//						e1.printStackTrace();
//					}
//		    		break;
//		    		
//		    	case "SeniorSystem":
//		    		try {
//						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), SystemEditor.ID);
//					} 
//					catch (PartInitException e1) {
//						e1.printStackTrace();
//					}
//		    		break;
//		    	
//		    	case "SeniorSubsystem":
//		    		try {
//						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), SubsystemEditor.ID);
//					} 
//					catch (PartInitException e1) {
//						e1.printStackTrace();
//					}
//		    		break;
//		    	case "SeniorMessage":
//		    		try {
//						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), MessageEditor.ID);
//					} 
//					catch (PartInitException e1) {
//						e1.printStackTrace();
//					}
//		    		break;
//		    		
//		    	case "SeniorField":
//		    		try {
//						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), FieldEditor.ID);
//					} 
//					catch (PartInitException e1) {
//						e1.printStackTrace();
//					}
//		    		break;
//		    		
//		    	default: break; 	
//		    	}
//			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}	
	}	

	public void createPartControl(Composite parent) {
		layout = new GridLayout();
		layout.numColumns = 6;
		parent.setLayout(layout);
		
		createButtons(parent);
		
		model = new SeniorModel[1];
		
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 6;
		treeViewer.getTree().setLayoutData(gridData);
		treeViewer.setLabelProvider(new ModelTreeLabelProvider());
		treeViewer.setContentProvider(new ModelTreeContentProvider());
		
		
//****************************************************[ add double click functionallity to open editors ] ********************************
		treeViewer.addDoubleClickListener(new IDoubleClickListener(){

			@Override
			public void doubleClick(DoubleClickEvent event) {
				BitField bitField;
				XMLView xml;
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		    	SeniorNode node = (SeniorNode) selection.getFirstElement();
//				try {
//					bitField = (BitField) getViewSite().getPage().showView(BitField.ID);
//					
//				} catch (PartInitException e) {
//					e.printStackTrace();
//					bitField = new BitField();
//				}
//		
//				try {
//					xml = (XMLView) getViewSite().getPage().showView(XMLView.ID);
//					
//				} catch (PartInitException e) {
//					xml = new XMLView();
//				}				
				
		    	switch(node.getClass().getSimpleName()){
		    	case "SeniorModel":
		    		try {
						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), ModelEditor.ID);
					} 
					catch (PartInitException e1) {
						e1.printStackTrace();
					}
		    		break;
		    	case "SeniorDomain": 
		    		try {
						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), DomainEditor.ID);
					} 
					catch (PartInitException e1) {
						e1.printStackTrace();
					}
		    		break;
		    		
		    	case "SeniorSystem":
		    		try {
						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), SystemEditor.ID);
					} 
					catch (PartInitException e1) {
						e1.printStackTrace();
					}
		    		break;
		    	
		    	case "SeniorSubsystem":
		    		try {
						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), SubsystemEditor.ID);
					} 
					catch (PartInitException e1) {
						e1.printStackTrace();
					}
		    		break;
		    	case "SeniorMessage":
		    		try {
						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), MessageEditor.ID);
					} 
					catch (PartInitException e1) {
						e1.printStackTrace();
					}
		    		break;
		    		
		    	case "SeniorField":
		    		try {
						getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), FieldEditor.ID);
					} 
					catch (PartInitException e1) {
						e1.printStackTrace();
					}
		    		break;
		    		
		    	default: break; 	
		    	}
				
				getViewSite().getPage().activate(getViewSite().getPage().findView(TreeView.ID));
			}
			
		});
		
//*******************************************************[ Check current selection to control buttons ]
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				SeniorNode node = (SeniorNode) selection.getFirstElement();
				if(node instanceof SeniorSystem){
					importButton.setEnabled(true);
					importButton.setToolTipText("Import existing " + node.getChildType());
					exportButton.setEnabled(false);
				}
				else if(node instanceof SeniorSubsystem){
					importButton.setEnabled(true);
					importButton.setToolTipText("Import existing " + node.getChildType());
					exportButton.setEnabled(true);
					exportButton.setToolTipText("Export " + node.getName());
				}
				else if(node instanceof SeniorMessage){
					importButton.setEnabled(false);
					exportButton.setEnabled(true);
					exportButton.setToolTipText("Export " + node.getName());
				}
				else{
					importButton.setEnabled(false);
					exportButton.setEnabled(false);
				}
			}
			
		});
		
//*****************************************************[ Allow user to press delete key for a tree selection ]**************************
		treeViewer.getTree().addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.character == 127){
					IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
					final SeniorNode node = (SeniorNode) selection.getFirstElement();
					if(node == null)
						return;
					closeEditors(node);							
					if(node instanceof SeniorModel){
            			importButton.setEnabled(true);
        				exportButton.setEnabled(false);
        				discardButton.setEnabled(false);
        				newButton.setEnabled(true);
        				//editButton.setEnabled(false);
        				
        				model[0] = null;
            			treeViewer.remove(treeViewer.getInput());
            		}
            		else
            			model[0].getParentNode(node).removeChild(node);
					treeViewer.setExpandedState(node,true);
	        		treeViewer.refresh(true);	
				}
									
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
		});
		
	//*********************************************[ set the tree as a selection provider so other workbench parts can listen ]
		getSite().setSelectionProvider(treeViewer);
		
		
//*********************************************************[ Add a menu to the treeViewer ]*****************************************
		final MenuManager mgr = new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
				final SeniorNode node = (SeniorNode) selection.getFirstElement();
				if (!selection.isEmpty()) {
					if(!(node instanceof SeniorField)){
						final Action a = new Action("") {
							public void run(){
								 if(node.getChildType().equals("SeniorDomain"))
				        			   node.addChild(new SeniorDomain());
				        		   else if(node.getChildType().equals("SeniorSystem"))
				        			   node.addChild(new SeniorSystem());
				        		   else if(node.getChildType().equals("SeniorSubsystem"))
				        			   node.addChild(new SeniorSubsystem());
				        		   else if(node.getChildType().equals("SeniorMessage"))
				        			   node.addChild(new SeniorMessage());
				        		   else if(node.getChildType().equals("SeniorField"))
				        			   node.addChild(new SeniorField());
				        		   
				        		   treeViewer.setExpandedState(node,true);
				        		   treeViewer.refresh(true);
							}
						};
						
						
						a.setText("Add child to " + node.getName());
						mgr.add(a);
					}
					
					else{
						final Action a = new Action("") {
							public void run(){
								BitField bitField;
								XMLView xml;
								SeniorMessage message = (SeniorMessage) model[0].getParentNode(node);
								if(message.getChildren().indexOf(node) == 0){
									return;
								}
								message.moveChildUp((SeniorField)node);
								treeViewer.setExpandedState(node,true);
				        		treeViewer.refresh(true);
				        		
				        		for(int i=0; i<PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences().length; i++){
				    				IEditorReference editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i];
				    				try {
				    					ModelEditorInput input = (ModelEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i].getEditorInput();
				    					IEditorPart ep = editor.getEditor(false);
				    					if(ep instanceof FieldEditor){
				    						((FieldEditor) ep).refresh();
				    					}
				    					
				    				} catch (PartInitException e) {
				    					e.printStackTrace();
				    				}
				    				
				    			}
				        		
				        		try {
				        			bitField = (BitField) page.showView(BitField.ID);
				    				
				    			} catch (PartInitException e) {
				    				// TODO Auto-generated catch block
				    				//e.printStackTrace();
				    				bitField = new BitField();
				    			}
			    			
				    			bitField.refresh(message);
				    			
				    			try {
				    				xml = (XMLView) page.showView(XMLView.ID);
				    				
				    			} catch (PartInitException e) {
				    				// TODO Auto-generated catch block
				    				xml = new XMLView();
				    			}
				    			
				    			xml.refresh(message);
							}
						};
						a.setText("Move " + node.getName() + " up one position.");
						mgr.add(a);
						
						final Action b = new Action("") {
							public void run(){
								BitField bitField;
								XMLView xml;
								SeniorMessage message = (SeniorMessage) model[0].getParentNode(node);
								if(message.getChildren().indexOf(node) == message.getChildren().size() - 1){
									return;
								}
								message.moveChildDown((SeniorField)node);
								treeViewer.setExpandedState(node,true);
				        		treeViewer.refresh(true);
				        		
				        		for(int i=0; i<PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences().length; i++){
				    				IEditorReference editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i];
				    				try {
				    					ModelEditorInput input = (ModelEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i].getEditorInput();
				    					IEditorPart ep =  editor.getEditor(false);
				    					if(ep instanceof FieldEditor)				    					
				    						((FieldEditor) ep).refresh();
				    				} catch (PartInitException e) {
				    					e.printStackTrace();
				    				}
				    				
				    			}
				        		
				        		try {
				        			bitField = (BitField) page.showView(BitField.ID);
				    				
				    			} catch (PartInitException e) {
				    				bitField = new BitField();
				    			}
				    			
				    			bitField.refresh(message);
				    			
				    			try {
				    				xml = (XMLView) page.showView(XMLView.ID);
				    				
				    			} catch (PartInitException e) {
				    				xml = new XMLView();
				    			}
				    			
				    			xml.refresh(message);
							}
						};
						b.setText("Move " + node.getName() + " down one position.");
						mgr.add(b);
					}
					
					final Action a = new Action("") {
						public void run(){
							closeEditors(node);							
							if(node instanceof SeniorModel){
		            			importButton.setEnabled(true);
		        				exportButton.setEnabled(false);
		        				discardButton.setEnabled(false);
		        				newButton.setEnabled(true);
		        				//editButton.setEnabled(false);
		        				
		        				model[0] = null;
		            			treeViewer.remove(treeViewer.getInput());
		            		}
		            		else
		            			model[0].getParentNode(node).removeChild(node);
							treeViewer.setExpandedState(node,true);
			        		treeViewer.refresh(true);
		            	}												
					};				
					a.setText("Delete " + node.getName());
					mgr.add(a);
					
					final Action b = new Action("") {
						public void run(){
							switch(node.getClass().getSimpleName()){
					    	case "SeniorModel":
					    		try {
									getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), ModelEditor.ID);
								} 
								catch (PartInitException e1) {
									e1.printStackTrace();
								}
					    		break;
					    	case "SeniorDomain": 
					    		try {
									getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), DomainEditor.ID);
								} 
								catch (PartInitException e1) {
									e1.printStackTrace();
								}
					    		break;
					    		
					    	case "SeniorSystem":
					    		try {
									getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), SystemEditor.ID);
								} 
								catch (PartInitException e1) {
									e1.printStackTrace();
								}
					    		break;
					    	
					    	case "SeniorSubsystem":
					    		try {
									getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), SubsystemEditor.ID);
								} 
								catch (PartInitException e1) {
									e1.printStackTrace();
								}
					    		break;
					    	case "SeniorMessage":
					    		try {
									getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), MessageEditor.ID);
								} 
								catch (PartInitException e1) {
									e1.printStackTrace();
								}
					    		break;
					    		
					    	case "SeniorField":
					    		try {
									getViewSite().getPage().openEditor(new ModelEditorInput(treeViewer), FieldEditor.ID);
								} 
								catch (PartInitException e1) {
									e1.printStackTrace();
								}
					    		break;
					    		
					    	default: break; 	
					    	}
		            	}												
					};				
					b.setText("Edit " + node.getName());
					mgr.add(b);
				}
			}
		});
		treeViewer.getControl().setMenu(mgr.createContextMenu(treeViewer.getControl()));
	
	}
	
	
	//recursively close the editors corresponding to the node passed and all of its descendants 
	public void closeEditors(SeniorNode parent){
		int i;
		ModelEditorInput input = null;
		IEditorReference editor = null;
		if(parent.getChildren().size() == 0){
			for(i=0; i<PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences().length; i++){
				editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i];
				try {
					input = (ModelEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i].getEditorInput();
					if(input.id == parent.getID())
						editor.getEditor(false).getEditorSite().getPage().closeEditor(editor.getEditor(false), true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
				
			}								
			return;
		}								
		for(i=0; i<parent.getChildren().size(); i++)
			closeEditors(parent.getChildren().get(i));
		for(i=0; i<PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences().length; i++){
			editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i];
			try {
				input = (ModelEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()[i].getEditorInput();
				//System.out.println(input.id);
				//System.out.println(parent.getID());
				if(input.id == parent.getID())
					editor.getEditor(false).getEditorSite().getPage().closeEditor(editor.getEditor(false), true);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	//create the buttons
	public void createButtons(Composite parent){
		handler = new ButtonHandler();
				
		newButton = new Button(parent, SWT.PUSH);
		newButton.setText("New");
		newButton.setToolTipText("Create a new model");
		newButton.addSelectionListener(handler);
		
		importButton = new Button(parent, SWT.PUSH);
		importButton.setText("Import");
		importButton.setToolTipText("Load an existing child from XML");
		importButton.addSelectionListener(handler);
		importButton.setEnabled(false);
		
		exportButton = new Button(parent, SWT.PUSH);
		exportButton.setText("Export");
		exportButton.setToolTipText("Export current selection");
		exportButton.addSelectionListener(handler);
		exportButton.setEnabled(false);
		
		discardButton = new Button(parent, SWT.PUSH);
		discardButton.setText("Discard");
		discardButton.setToolTipText("Dicard current model");
		discardButton.addSelectionListener(handler);
		discardButton.setEnabled(false);
		
//		editButton = new Button(parent, SWT.PUSH);
//		editButton.setText("Edit");
//		editButton.setToolTipText("Edit Selected Item");
//		editButton.addSelectionListener(handler);
//		editButton.setEnabled(false);

	}
	public TreeViewer getTree(){
		
		return this.treeViewer;
	}
	
	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
	
}


