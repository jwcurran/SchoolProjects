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
public class SeniorProtocol extends SeniorNode{
	
	//private data members
	//static private int nextIndex;	
	//public methods
	
	public SeniorProtocol(){
		super("Unknown Protocol", new LinkedList<SeniorNode>() );
		setChildType("SeniorField");
	}
	public SeniorProtocol(String name,  LinkedList<? extends SeniorNode> children) {
		super(name,(LinkedList<SeniorNode>) children);
		setChildType("SeniorField");
	}
	
	@Override
	public boolean addChild(SeniorNode child){
		int i;
		boolean output = false;
		
		if(child.getClass().getSimpleName().equals(ChildType)){
			
			
			if(this.getChildren().size() <= 0){
				this.getChildren().add(child);
				output = true;
			} else{
				i = getInsertIndex( (SeniorField)child );
				if(isCollision(i, (SeniorField) child)){
					System.err.println("Collision while adding " + child.getTestString()+ " at index " + i );
				}else{
					this.getChildren().add(i, child);
					output = true;
				}
			}
		}
		else {
				System.err.println("Failed to add " + child.getTestString()+ " to " + this.getTestString() +
						"\nFailure was\n|" +child.getClass().getSimpleName() + "!=" + ChildType +"|" );
		}
			
		return output;
	}
	@Override
	public void removeChild(SeniorNode child) {
		if(this.getChildren().contains(child)){
			this.getChildren().remove(child);	
		}else{
			System.err.println("failed to remove child: " + child);
		}
		
	}
	public void moveChildUp(SeniorField moveChild){
		int absDiff;
		int tempIndex;
		SeniorField prevChild;
		
		if(this.getChildren().contains(moveChild) ){
			tempIndex = this.getChildren().indexOf(moveChild);
			if(tempIndex == 0){
				return;
			}
			
			prevChild = ((SeniorField)(this.getChildren().get(tempIndex -1)));
			//absDiff = moveChild.getAbsPos() - prevChild.getAbsPos();
			absDiff = moveChild.getLength();
			
			moveChild.setWordPos(prevChild.getWordPos());
			moveChild.setBitPos(prevChild.getBitPos());
			
			prevChild.setWordPos(prevChild.getWordPos() + (absDiff/SeniorField.wordSize));
			prevChild.setBitPos(prevChild.getBitPos() + (absDiff%SeniorField.wordSize) );
			
			this.getChildren().set(tempIndex, prevChild);
			this.getChildren().set(tempIndex - 1 , moveChild);
		}
	}
	public void moveChildDown(SeniorField moveChild){
		int tempIndex;
		SeniorField nextChild;
		
		if(this.getChildren().contains(moveChild) ){
			tempIndex = this.getChildren().indexOf(moveChild);
			if(tempIndex == this.getChildren().size() -1){
				return;
			}
			
			nextChild = ((SeniorField)(this.getChildren().get(tempIndex + 1)));
			this.moveChildUp(nextChild);
		}
	}
	
	public int getInsertIndex(SeniorField child){
		int output = -1;//-1 indicates error
		
			if(this.getChildren().size() == 0){
				output = 0;
			} else if ( ((SeniorField)(this.getChildren().get( 0 ))).getAbsPos() > child.getAbsPos() ){
				output= 0;		
			} else if ( ((SeniorField)(this.getChildren().get(this.getChildren().size() -1 ))).getAbsPos() < child.getAbsPos() ){
				output = this.getChildren().size(); 
			}else {
				output = 0;
				while (((SeniorField)(this.getChildren().get(output ))).getAbsPos() < child.getAbsPos()) {
	                output++;
	            }	
			}		
		return output;
	}
	
	public boolean isCollision(int index, SeniorField child){
		boolean output = true;
		int diff = 0;
		if(index < 0){
			;
		}else if(index == 0){
			diff = ((SeniorField)(this.getChildren().get( 0 ))).getAbsPos();
		}
		else if( index >= this.getChildren().size() ){
			diff = child.getLength() +1 ;
		}else{
			diff = ((SeniorField)(this.getChildren().get( index ))).getAbsPos() - ((SeniorField)(this.getChildren().get( index -1 ))).getAbsPos() -1;
		}
		
		if( diff >= child.getLength() ){
			output = false;
		}
		
		return output;
	}
	
}
