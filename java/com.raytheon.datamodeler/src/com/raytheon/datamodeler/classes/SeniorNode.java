package com.raytheon.datamodeler.classes;

import java.util.LinkedList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.StringWriter;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
//public abstract class SeniorNode
public class SeniorNode{
	private String name;
	private static int counter = 0;
	private int id;
	
	@XmlElementWrapper(name = "children")
    @XmlElement(name = "child")
	private LinkedList<SeniorNode> children;
	
	//private SeniorNode parentNode;//set to self until node is added via the addChild method
	//public members
	public String ChildType = null;//This is used for type safety all the children must be of this type
	
	public SeniorNode(){//needs to be extended to set childType to appropriate value 
		super();
		this.name = "Unknown abstract name";
		this.children = new LinkedList<SeniorNode>();
		id = counter++;	
		//parentNode = this;
	}
	
	public SeniorNode(String name, LinkedList<? extends SeniorNode> children) {//needs to be extended to set childType to appropriate value
		super();
		this.name = name;
		this.children = (LinkedList<SeniorNode>) children;
		id = counter++;	
		//parentNode = this;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<SeniorNode> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<? extends SeniorNode> children) {
		this.children = (LinkedList<SeniorNode>) children;
	}
	public boolean addChild(SeniorNode child) {
		if(child.getClass().getSimpleName().equals(ChildType)){
			//child.setParentNode(this);
			children.add(child);
			return true;
		}	
		else {
			System.err.println("Failed to add " + child.getTestString()+ " to " + this.getTestString() +
					"\nFailure was\n|" +child.getClass().getSimpleName() + "!=" + ChildType +"|" );
			return false;
		}
									
	}
	public void removeChild(SeniorNode child) {
		children.remove(child);
	}
	public  String getChildType() {
		return ChildType;
	}
	public void setChildType(String childType) {
		ChildType = childType;
	}
	
	public SeniorNode getParentNode(SeniorNode key) {
		SeniorNode output= null;
		
		if(this.getChildren().contains(key)){
			output = this;
		}
		else{
			for(int i =0; i<this.getChildren().size();i++){
				output = this.getChildren().get(i).getParentNode(key);
				if(output != null ){
					break;
				}
			}
		}
		
		return output;
	}
//
//	public void setParentNode(SeniorNode parentNode) {
//		this.parentNode = parentNode;
//	}

	public String getTestString() {
			return  this.getClass().getSimpleName() + "@" + this.toString() + "[Name= " + name + ", Number of Children= " + children.size() +" Children= " + children +"]";// + ", parentNode=" + parentNode + "]";
	}
	
	public String getRecursiveTestString() {
		String output = new String();
		output = this.getClass().getSimpleName() + "@" + this.toString() + "[Name= " + name + ", Number of Children= " + children.size() +" Children= " + children +"]";// + ", parentNode=" + parentNode + "]";
		if(children.size() > 0){
			for(int i = 0; i<children.size(); i++){
				output += "\n" + this.getChildren().get(i).getRecursiveTestString();
			}
		}
		return  output;
}
	
	public String getXMLString(){
		String temp = "";
		
		try{
		
			//JAXBContext jc = JAXBContext.newInstance( com.raytheon.datamodeler.classes.SeniorNode.class, this.getClass());
			JAXBContext jc = JAXBContext.newInstance( com.raytheon.datamodeler.classes.SeniorNode.class, com.raytheon.datamodeler.classes.SeniorDomain.class, com.raytheon.datamodeler.classes.SeniorField.class, com.raytheon.datamodeler.classes.SeniorMessage.class, com.raytheon.datamodeler.classes.SeniorMessage.class, com.raytheon.datamodeler.classes.SeniorModel.class, com.raytheon.datamodeler.classes.SeniorNumericField.class, com.raytheon.datamodeler.classes.SeniorProtocol.class, com.raytheon.datamodeler.classes.SeniorSubsystem.class, com.raytheon.datamodeler.classes.SeniorSystem.class);
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
		
			m.marshal( this, writer );
			temp = writer.toString();
			writer.close();
		}
		
			catch(JAXBException je){
				
				je.printStackTrace();
			  	
			} catch (IOException e) {
				e.printStackTrace();
			}		
		return temp;
	}

	
	static public  SeniorNode getObjectFromXML(String path){
			SeniorNode t=  new SeniorNode();
		try{
			JAXBContext context = JAXBContext.newInstance(com.raytheon.datamodeler.classes.SeniorNode.class, com.raytheon.datamodeler.classes.SeniorDomain.class, com.raytheon.datamodeler.classes.SeniorField.class, com.raytheon.datamodeler.classes.SeniorMessage.class, com.raytheon.datamodeler.classes.SeniorMessage.class, com.raytheon.datamodeler.classes.SeniorModel.class, com.raytheon.datamodeler.classes.SeniorNumericField.class, com.raytheon.datamodeler.classes.SeniorProtocol.class, com.raytheon.datamodeler.classes.SeniorSubsystem.class, com.raytheon.datamodeler.classes.SeniorSystem.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			//StringReader reader = new StringReader(path);
			FileReader freader = new FileReader(path);
			t = (SeniorNode)unmarshaller.unmarshal(freader);
			freader.close();
		 
		}
		catch (JAXBException e){
			System.err.println( "The String is\n" + e.toString() );
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return (t);
	}
	
	public int getID(){
		return this.id;	
	}
}
