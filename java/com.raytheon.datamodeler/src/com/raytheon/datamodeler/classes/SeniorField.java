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


//@XmlRootElement
public class SeniorField extends SeniorNode {
	static final int wordSize = 8;
	
	//private data Members
	//private int index;
	private String dataType;
	private int wordPos;
	private int bitPos;
	private int absPos;
	private int length;
	private double rangeMin;
	private double rangeMax;
	private boolean isNumber;
	
	
	
	//private methods
	private int calcNewAbsPos(){
		if(wordPos == -1 || bitPos == -1){
			return -1;
		}
		return ( (wordPos*wordSize) + bitPos);
	}
	
	//public methods
	
	public SeniorField(){
		super("Unknown Field", new LinkedList<SeniorNode>());
		//index = 0;
		dataType = "unknown data";
		wordPos = -1;
		bitPos = -1;
		absPos = -1;
		length = -1;
		isNumber = false;
		rangeMin = -1;
		rangeMax = -1;
		ChildType = "";
		
	}
	
	public SeniorField(String fieldName,int fieldIndex, String fieldType, int fieldWordPos, int fieldBitPos, int fieldLength ){
		super(fieldName, new LinkedList<SeniorNode>() );
		//index = fieldIndex;
		dataType = fieldType;
		wordPos = fieldWordPos;
		bitPos = fieldBitPos;
		length = fieldLength;
		absPos = calcNewAbsPos();
		isNumber = false;
		rangeMin = -1;
		rangeMax = -1;
		ChildType = "";
		
	}
	
	public int getIndex(){
		//return index;
//		if(this.getParentNode() != null){
//			return this.getParentNode().getChildren().indexOf(this);
//		}else{
			return -1;
		//}
	}
//	public void setIndex(int x){
//		index = x;
//	}
	
	public String getDataType(){
		return dataType;
	}
	public void setDataType(String x){
		dataType = x;
	}
	
	public int getWordPos(){
		return wordPos;
	}
	public void setWordPos(int x){
		wordPos = x;
		absPos = calcNewAbsPos();
	}
	
	public int getBitPos(){
		return bitPos;
	}
	public void setBitPos(int x){
		bitPos = x;
		absPos = calcNewAbsPos();
		
	}
	
	public int getAbsPos(){
		return absPos;
	}
	
	public int getLength(){
		return length;
	}
	public void setLength(int x){
		length = x;
	}
	
	@Override
	public String getTestString(){
//		return ( "Name: " + getName()+"\nIndex: " + getIndex()+ "\nData Type: "+
//				getdataType() + "\nWord Positions: " + getWordPos() + 
//				"\nBit Position: " + getBitPos() + "\nAbsolute Positions: " + 
//				getAbsPos() + "\nLength: " + getLength());
		return this.getClass().getSimpleName() + "@" + this.toString() + " [index=" + this.getIndex() + ", dataType=" + dataType
				+ ", wordPos=" + wordPos + ", bitPos=" + bitPos + ", absPos= "
				+ absPos + ", length= " + length + " isNumber: " + isNumber + " rangeMin: " + rangeMin + " rangeMax: " + rangeMax +"]";
				//", parentNode=" +  +this.getParentNode().toString() + "]";
	}
	
	public double getRangeMin() {
		return rangeMin;
	}

	public void setRangeMin(double rangeMin) {
		if(this.isNumber() == true){
			this.rangeMin = rangeMin;
		}
		else{
//			System.err.println("Cannot Adjust min range for non-Numeric Types @ " + this.getTestString() );
		}
	}

	public double getRangeMax() {
		return rangeMax;
	}

	public void setRangeMax(double rangeMax) {
		if(this.isNumber() == true){
			this.rangeMax = rangeMax;
		}
		else{
//			System.err.println("Cannot Adjust max range for non-Numeric Types @ " + this.getTestString() );
		}
		this.rangeMin = rangeMax;
		
	}

	public boolean isNumber() {
		return isNumber;
	}

	public void setNumber(boolean isNumber) {
		this.isNumber = isNumber;
	}

	@Override
	public String getRecursiveTestString() {
		return this.getTestString();
	}
	
	@Override
	public boolean addChild(SeniorNode child){
		return false;
	}


}//end of class
