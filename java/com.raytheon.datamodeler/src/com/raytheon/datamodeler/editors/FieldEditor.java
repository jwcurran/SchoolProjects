package com.raytheon.datamodeler.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import com.raytheon.datamodeler.classes.SeniorField;
import com.raytheon.datamodeler.classes.SeniorMessage;
import com.raytheon.datamodeler.classes.SeniorModel;
import com.raytheon.datamodeler.views.BitField;
import com.raytheon.datamodeler.views.Popup;
import com.raytheon.datamodeler.views.TreeView;
import com.raytheon.datamodeler.views.XMLView;

public class FieldEditor extends EditorPart {
	public static final String ID = "com.raytheon.datamodeler.editors.fieldeditor";
	
	private ModelEditorInput input;
	private TreeViewer treeViewer;
	private boolean isPageModified;
	
	private Text nameText;
	private Label nameLabel;
	private Label dataTypeLabel;
	private Combo dataTypeCombo;
	private Label rangeMinLabel;
	private Text rangeMinText;
	private Label rangeMaxLabel;
	private Text rangeMaxText;
	private Label bitPositionLabel;
	private Text bitPositionText;
	private Label wordPositionLabel;
	private Text wordPositionText;
	private Button packButton;
	private Label maxLengthLabel;
	private Text maxLengthText;
	
	private BitField bitField;
	private XMLView xml;
	
	private IWorkbench wb = PlatformUI.getWorkbench();
	private IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	private IWorkbenchPage page = win.getActivePage();
	
	private SeniorField field;

	public FieldEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		this.setPartName(nameText.getText());
		field.setName(nameText.getText());
		String dataType = dataTypeCombo.getText();
		double rangeMin;
		double rangeMax;
		int bitPos;
		int wordPos;
		int maxLength;
		
		if(dataType != null){
			field.setDataType(dataType);
		}
		dataTypeLabel.setText("Data Type: " + field.getDataType());
		
		//handle exceptions for non integer inputs of bit and word position, check that both are nonnegative
		try{
			bitPos = Integer.parseInt(bitPositionText.getText());
			wordPos = Integer.parseInt(wordPositionText.getText());
			
			if(bitPos < 0 || wordPos < 0){
				Popup pop = new Popup("Negative bit or word position not allowed");
				pop.run();
				field.setWordPos(-1);
				field.setBitPos(-1);
				//field.setLength(-1);
				wordPositionText.setText(String.valueOf(-1));
				bitPositionText.setText(String.valueOf(-1));
			}
			else{
				field.setBitPos(bitPos);
				field.setWordPos(wordPos);
			}
		}
		
		catch (NumberFormatException e){
			Popup pop = new Popup("Bit/Word pos must be int");
			pop.run();
		}
		
		//check for numeric datatypes
		if(dataType.equals("short") || dataType.equals("unsigned short") || dataType.equals("long") || dataType.equals("unsigned long") || dataType.equals("long long") || dataType.equals("unsigned long long") || dataType.equals("float") || dataType.equals("double") || dataType.equals("long double")){
			field.setNumber(true);
			
			//exception handle for non numeric input, check that max > min, check unsigned type gets unsigned range, 
			//check int type gets int range. if not numeric type, set values accordingly 
			
			try{
				rangeMin = Double.parseDouble(rangeMinText.getText());
				rangeMax = Double.parseDouble(rangeMaxText.getText());
				
				if((rangeMax < 0 || rangeMin < 0) && dataType.contains("unsigned")){
					Popup pop = new Popup("Signed value !-> unsigned type");
					pop.run();
					field.setRangeMax(-1);
					field.setRangeMin(-1);
					rangeMaxText.setText(String.valueOf(field.getRangeMax()));
					rangeMinText.setText(String.valueOf(field.getRangeMin()));
				}
				
				else if(rangeMax < rangeMin){
					Popup pop = new Popup("Max less than min");
					pop.run();
					field.setRangeMax(-1);
					field.setRangeMin(-1);
					rangeMaxText.setText(String.valueOf(field.getRangeMax()));
					rangeMinText.setText(String.valueOf(field.getRangeMin()));
				}
				
				else if(rangeMax == 0 && rangeMin == 0){
					Popup pop = new Popup("Only zero possible, no data");
					pop.run();
					field.setRangeMax(-1);
					field.setRangeMin(-1);
					rangeMaxText.setText(String.valueOf(field.getRangeMax()));
					rangeMinText.setText(String.valueOf(field.getRangeMin()));
				}
				
				else{
					if((dataType.equals("short") || dataType.equals("unsigned short") || dataType.equals("long") || dataType.equals("unsigned long") || dataType.equals("long long") || dataType.equals("unsigned long long")) && (rangeMax%1 != 0 || rangeMin%1 != 0)){
						Popup pop = new Popup("Non-int -> int type");
						pop.run();
					}					
					else{
						field.setRangeMax(rangeMax);
						field.setRangeMin(rangeMin);
					}
				}
				
			}
			
			catch (NumberFormatException e){
				Popup pop = new Popup("Range min/max must be number");
				pop.run();
			}
			
		}
		
		
		else{
			field.setNumber(false);
			field.setRangeMax(-1);
			field.setRangeMin(-1);
		}
		
		rangeMax = field.getRangeMax();
		rangeMin = field.getRangeMin();
		if(rangeMax%1 == 0)
			rangeMaxText.setText(String.valueOf((int)rangeMax));
		else
			rangeMaxText.setText(String.valueOf(rangeMax));
		if(rangeMin%1 == 0)
			rangeMinText.setText(String.valueOf((int)rangeMin));
		else
			rangeMinText.setText(String.valueOf(rangeMin));
		//First assign a data type its native size to check if number can be represented with selected data type. If it cannot, show error
		//and disable packbits button. If it can, and packBits is enabled, pack the bits. 3 cases for packing bits. One for unsigned and 
		//two for signed.
			switch(field.getDataType()){
				case "short": field.setLength(16);
					break;
				case "unsigned short": field.setLength(16);
					break;
				case "long": field.setLength(32);
					break;
				case "unsigned long": field.setLength(32);
					break;
				case "long long": field.setLength(64);
					break;
				case "unsigned long long": field.setLength(64);
					break;
				case "float": field.setLength(32);
					break;
				case "double": field.setLength(64);
					break;
				case "long double": field.setLength(79);
					break;
				case "char": field.setLength(8);
					break;
				case "wchar":
					try{
						maxLength = Integer.parseInt(maxLengthText.getText());
						if(maxLength <= 0 ){
							Popup pop = new Popup("Max length must be greater than 0");
							pop.run();
						}
						else if(maxLength %1 != 0){
							Popup pop = new Popup("Max length must be valid int for this type");
							pop.run();
						}
						else{
							field.setLength(maxLength);
						}
						
					}catch(NumberFormatException e){
						Popup pop = new Popup("Max length must be valid int for this type");
						pop.run();
					}
					break;
				case "string":
					try{
						maxLength = Integer.parseInt(maxLengthText.getText());
						if(maxLength <= 0 ){
							Popup pop = new Popup("Max length must be greater than 0");
							pop.run();
						}
						else if(maxLength %1 != 0){
							Popup pop = new Popup("Max length must be valid int for this type");
							pop.run();
						}
						else{
							field.setLength(maxLength);
						}
						
					}catch(NumberFormatException e){
						Popup pop = new Popup("Max length must be valid int for this type");
						pop.run();
					}
					break;
				case "boolean":
					try{
						maxLength = Integer.parseInt(maxLengthText.getText());
						if(maxLength <= 0 ){
							Popup pop = new Popup("Max length must be greater than 0");
							pop.run();
						}
						else if(maxLength %1 != 0){
							Popup pop = new Popup("Max length must be valid int for this type");
							pop.run();
						}
						else{
							field.setLength(maxLength);
						}
						
					}catch(NumberFormatException e){
						Popup pop = new Popup("Max length must be valid int for this type");
						pop.run();
					}
					break;
				case "octet": field.setLength(8);
					break;
				 default: field.setLength(8);
				 	break;
				 	
				 case "any":
						try{
							maxLength = Integer.parseInt(maxLengthText.getText());
							if(maxLength <= 0 ){
								Popup pop = new Popup("Max length must be greater than 0");
								pop.run();
							}
							else if(maxLength %1 != 0){
								Popup pop = new Popup("Max length must be valid int for this type");
								pop.run();
							}
							else{
								field.setLength(maxLength);
							}
							
						}catch(NumberFormatException e){
							Popup pop = new Popup("Max length must be valid int for this type");
							pop.run();
						}
						break;
			}
			
			if(dataType.equals("short") || dataType.equals("unsigned short") || dataType.equals("long") || dataType.equals("unsigned long") || dataType.equals("long long") || dataType.equals("unsigned long long") || dataType.equals("float") || dataType.equals("double") || dataType.equals("long double")){
				try{
					double max = field.getRangeMax();
					double min = field.getRangeMin(); 
					double intMax;
					double absMax;
					double log;
					
					if(Math.abs(max) > Math.abs(min)){
						absMax = Math.abs(max);
						intMax = max;
					}
					else{
						absMax = Math.abs(min);
						intMax = min;
					}
					if(field.getDataType().contains("unsigned")){
						log = Math.log(absMax+1)/Math.log(2);
					}
					
					else{
						if(intMax > 0)
							log = Math.log(absMax+1)/Math.log(2) + 1;
						else
							log = Math.log(absMax)/Math.log(2) + 1;
					}
					
					if(log%1 != 0)
						log = (int)log+1;
					else
						log = (int)log;
					
					if(field.getLength() < log){
						Popup pop = new Popup("Range too large for type");
						pop.run();
						field.setRangeMax(-1);
						field.setRangeMin(-1);
						rangeMaxText.setText(String.valueOf(field.getRangeMax()));
						rangeMinText.setText(String.valueOf(field.getRangeMin()));
						packButton.setSelection(false);
					}

						
			}catch(NumberFormatException e){
				Popup pop = new Popup("Range min/max must be number");
				pop.run();
			}
			}
			
				
		
		if(packButton.getSelection() == true){
			try{
				double max = field.getRangeMax();
				double min = field.getRangeMin(); 
				double intMax;
				double absMax;
				double log;
				
				if(Math.abs(max) > Math.abs(min)){
					absMax = Math.abs(max);
					intMax = max;
				}
				else{
					absMax = Math.abs(min);
					intMax = min;
				}
				if(field.getDataType().contains("unsigned")){
					log = Math.log(absMax+1)/Math.log(2);
				}
				
				else{
					if(intMax > 0)
						log = Math.log(absMax+1)/Math.log(2) + 1;
					else
						log = Math.log(absMax)/Math.log(2) + 1;
				}
				if(log%1 == 0)
					field.setLength((int) log);
				else
					field.setLength((int) log + 1);
		}catch(NumberFormatException e){
			Popup pop = new Popup("Range min/max must be number");
			pop.run();
		}
	}
		
		
		SeniorModel model[] = (SeniorModel[])treeViewer.getInput();
		SeniorMessage message = (SeniorMessage) model[0].getParentNode(field);
		
		
		//after all of this, the field must be removed from its parent and readded to check for bit collisions. If a collision occurs,
		//show an error and reset the fields bit position and word position to the default values. Then refresh all views.
		SeniorField temp = (SeniorField) message.getChildren().remove(message.getChildren().indexOf(field));
		if(message.addChild(temp)){	
		}
		
		else{
			field.setBitPos(-1);
			field.setWordPos(-1);
			field.setLength(-1);
			bitPositionText.setText("-1");
			wordPositionText.setText("-1");
			message.addChild(field);
			Popup pop = new Popup("Bit Collision!");
			pop.run();
		}
		
		try {
			bitField = (BitField) page.showView(BitField.ID);
			
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bitField.refresh(message);
	
		try {
			xml = (XMLView) page.showView(XMLView.ID);
			
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		xml.refresh(message);		
		treeViewer.refresh();
		isPageModified = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}
	
	//This is called when move up or move down is called on a field in the tree view.
	public void refresh(){
			wordPositionText.setText(String.valueOf(field.getWordPos()));
			bitPositionText.setText(String.valueOf(field.getBitPos()));
			isPageModified = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub
		if(!(input instanceof ModelEditorInput))
			throw new RuntimeException("Invalid Input: Must be ModelEditorInput");
		this.input = (ModelEditorInput) input;
		treeViewer = this.input.getTree();
		setSite(site);
		setInput(input);

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return isPageModified;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	//initiate all of the part's widgets and controls
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

		
		Image background = new Image(Display.getCurrent(), ModelEditor.class.getResourceAsStream("raytheon2.jpg"));
		parent.setBackgroundImage(background);
		parent.setLayout(null);
		final IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
		
		field = (SeniorField) selection.getFirstElement();
		if(field == null)
			this.getEditorSite().getPage().closeEditor(this, false);
			
		setPartName("Field Editor");
		
		nameLabel = new Label(parent, SWT.NONE);
		nameLabel.setText("Name: ");
		nameLabel.setBounds(20,35,40,20);
					
		nameText = new Text(parent, SWT.BORDER);
		nameText.setText(field.getName());
		nameText.setBounds(60,35,250,20);
		nameText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				boolean wasDirty = isDirty();
				isPageModified = true;
				if(!wasDirty){
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		nameText.addKeyListener(new KeyListener(){
			
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.character == 13)
					doSave(null);					
			}
			public void keyReleased(KeyEvent e) {
			}
			
		});

		dataTypeLabel = new Label(parent,SWT.NONE);
		dataTypeLabel.setText("Data Type: " + field.getDataType());
		dataTypeLabel.setBounds(20,90,200,20);
		
		rangeMinLabel = new Label(parent, SWT.NONE);
		rangeMinLabel.setText("Data Minimum: ");
		rangeMinLabel.setBounds(250, 90, 100, 20);
		rangeMinLabel.setEnabled(false);
			
		rangeMinText = new Text(parent, SWT.BORDER);
		rangeMinText.setText(String.valueOf(field.getRangeMin()));
		rangeMinText.setBounds(250,110,100,20);
		rangeMinText.setEnabled(false);
		rangeMinText.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.character == 13)
					doSave(null);					
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub	
			}
			
		});
		rangeMinText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				boolean wasDirty = isDirty();
				isPageModified = true;
				if(!wasDirty){
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		
		rangeMaxLabel = new Label(parent, SWT.NONE);
		rangeMaxLabel.setText("Data Maximum: ");
		rangeMaxLabel.setBounds(380, 90, 100, 20);
		rangeMaxLabel.setEnabled(false);
		
		rangeMaxText = new Text(parent, SWT.BORDER);
		rangeMaxText.setText(String.valueOf(field.getRangeMax()));
		rangeMaxText.setBounds(380,110,100,20);
		rangeMaxText.setEnabled(false);
		rangeMaxText.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.character == 13)
					doSave(null);					
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub	
			}			
		});
		
		rangeMaxText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				boolean wasDirty = isDirty();
				isPageModified = true;
				if(!wasDirty){
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		
		
		if(field.getDataType().equals("short") || field.getDataType().equals("unsigned short") || field.getDataType().equals("long") || field.getDataType().equals("unsigned long") || field.getDataType().equals("long long") || field.getDataType().equals("unsigned long long") || field.getDataType().equals("float") || field.getDataType().equals("double") || field.getDataType().equals("long double")){
			rangeMinLabel.setEnabled(true);
			rangeMinText.setEnabled(true);
			rangeMaxLabel.setEnabled(true);
			rangeMaxText.setEnabled(true);
		}
		
					
		dataTypeCombo = new Combo(parent, SWT.READ_ONLY);
		dataTypeCombo.setItems(new String[]{"short", "unsigned short", "long", "unsigned long", "long long", "unsigned long long", "float", "double",
				"long double", "char", "wchar", "string", "boolean", "octet", "any"});
		dataTypeCombo.setBounds(20, 110, 200, 30);
		dataTypeCombo.setText(field.getDataType());
		dataTypeCombo.addListener(SWT.Selection, new Listener(){
			
			public void handleEvent(Event e){				
				String temp = dataTypeCombo.getItems()[dataTypeCombo.getSelectionIndex()];

				if(temp.equals("short") || temp.equals("unsigned short") || temp.equals("long") || temp.equals("unsigned long") || temp.equals("long long") || temp.equals("unsigned long long") || temp.equals("float") || temp.equals("double") || temp.equals("long double")){
					if(temp.equals("short") || temp.equals("unsigned short") || temp.equals("long") || temp.equals("unsigned long") || temp.equals("long long") || temp.equals("unsigned long long"))
						packButton.setEnabled(true);
					else{
						packButton.setEnabled(false);
						packButton.setSelection(false);
					}
					maxLengthLabel.setEnabled(false);
					maxLengthText.setEnabled(false);
					maxLengthText.setText(String.valueOf(-1));
					rangeMinLabel.setEnabled(true);
					rangeMinText.setEnabled(true);
					rangeMaxLabel.setEnabled(true);
					rangeMaxText.setEnabled(true);						
				}				
				else if(temp.equals("wchar") || temp.equals("string") || temp.equals("boolean") || temp.equals("any")){
					maxLengthLabel.setEnabled(true);
					maxLengthText.setEnabled(true);
					packButton.setSelection(false);
					packButton.setEnabled(false);
					rangeMinLabel.setEnabled(false);
					rangeMinText.setEnabled(false);
					rangeMinText.setText(String.valueOf(-1));
					rangeMaxLabel.setEnabled(false);
					rangeMaxText.setEnabled(false);
					rangeMaxText.setText(String.valueOf(-1));
				}	
				else{
					packButton.setSelection(false);
					packButton.setEnabled(false);
					rangeMinLabel.setEnabled(false);
					rangeMinText.setEnabled(false);
					rangeMinText.setText(String.valueOf(-1));
					rangeMaxLabel.setEnabled(false);
					rangeMaxText.setEnabled(false);
					rangeMaxText.setText(String.valueOf(-1));
					maxLengthLabel.setEnabled(false);
					maxLengthText.setEnabled(false);
					maxLengthText.setText(String.valueOf(-1));
				}
				
			
			}
		
		});
		dataTypeCombo.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.character == 13)
					doSave(null);					
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		
		dataTypeCombo.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				boolean wasDirty = isDirty();
				isPageModified = true;
				if(!wasDirty){
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		
		
		bitPositionLabel = new Label(parent, SWT.NONE);
		bitPositionLabel.setText("Bit Position: ");
		bitPositionLabel.setBounds(140, 170, 100, 20);
		
		bitPositionText = new Text(parent, SWT.BORDER);
		new String();
		bitPositionText.setText(String.valueOf(field.getBitPos()));
		bitPositionText.setBounds(140,190,100,20);
		bitPositionText.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.character == 13)
					doSave(null);					
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		
		bitPositionText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				boolean wasDirty = isDirty();
				isPageModified = true;
				if(!wasDirty){
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		
		wordPositionLabel = new Label(parent, SWT.NONE);
		wordPositionLabel.setText("Word Position: ");
		wordPositionLabel.setBounds(20, 170, 100, 20);
		wordPositionText = new Text(parent, SWT.BORDER);
		new String();
		wordPositionText.setText(String.valueOf(field.getWordPos()));
		wordPositionText.setBounds(20,190,100,20);
		wordPositionText.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.character == 13)
					doSave(null);					
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		
		wordPositionText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				boolean wasDirty = isDirty();
				isPageModified = true;
				if(!wasDirty){
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		
		packButton = new Button(parent, SWT.CHECK);
		packButton.setText("Pack Bits ");
		packButton.setBounds(260, 190, 75, 20);
		packButton.setToolTipText("Pack field into smallest possible number of bits" + '\n' + " for the given range of values.");
		packButton.setEnabled(false);
		
		maxLengthLabel = new Label(parent, SWT.NONE);
		maxLengthLabel.setText("Max number of bits: ");
		maxLengthLabel.setBounds(350,170,130,20);
		maxLengthLabel.setEnabled(false);
		maxLengthText = new Text(parent,SWT.BORDER);
		maxLengthText.setBounds(350,190,110,20);
		maxLengthText.setEnabled(false);
		maxLengthText.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.character == 13)
					doSave(null);					
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		
		maxLengthText.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				boolean wasDirty = isDirty();
				isPageModified = true;
				if(!wasDirty){
					firePropertyChange(IEditorPart.PROP_DIRTY);
				}
			}
		});
		
		
		
		Button saveButton = new Button(parent,SWT.PUSH);
		saveButton.setText("Save");
		saveButton.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				doSave(null);
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		
		saveButton.setBounds(20,300,50,30);
		saveButton.setToolTipText("Save changes.");
		
		
		Button deleteButton = new Button(parent,SWT.PUSH);
		deleteButton.setText("Delete");
		deleteButton.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent e) {
				try {
					((TreeView) page.showView(TreeView.ID)).closeEditors(field);
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}							
				((SeniorModel[])treeViewer.getInput())[0].getParentNode(field).removeChild(field);
				treeViewer.setExpandedState(field,true);
        		treeViewer.refresh(true);
			}
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		
		deleteButton.setBounds(90,300,50,30);
		deleteButton.setToolTipText("Delete this node and all descendants");
		

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}