package com.raytheon.datamodeler.classes;


public class SeniorNumericField extends SeniorField {
	
	
	//private members
	private double rangeMin;
	private double rangeMax;
	
	//public methods
	public SeniorNumericField(){
		
		super();
		rangeMin = 0;
		rangeMax = 0;
		
	}
	
	public SeniorNumericField( String fieldName,int fieldIndex, String fieldType, int fieldWordPos, int fieldBitPos, int fieldLength, double fieldMin, double fieldMax){
		
		super(fieldName, fieldIndex,fieldType,fieldWordPos,fieldBitPos,fieldLength);
		rangeMin = fieldMin;
		rangeMax = fieldMax;
		
	}
	
	public double getRangeMin(){
		return rangeMin;
	}
	public void setRangeMin(double x){
		rangeMin = x;
	}
	
	public double getRangeMax(){
		return rangeMax;
	}
	public void setRangeMax(double x){
		rangeMax = x;
	}
	
	public String getTestString(){
//		return( "Name: " + getName()+"\nIndex: " + getIndex()+ "\nData Type: "+
//				getdataType() + "\nWord Positions: " + getWordPos() + 
//				"\nBit Position: " + getBitPos() + "\nAbsolute Positions: " + 
//				getAbsPos() + "\nLength: " + getLength() + "\nMin: " + 
//				getRangeMin() + "\nMax: " + getRangeMax() );
		return this.getClass().getSimpleName() + "@" + this.toString() + " [index=" + this.getIndex() + ", dataType=" + this.getDataType()
				+ ", wordPos=" + this.getWordPos() + ", bitPos=" + this.getBitPos() + ", absPos="
				+ this.getAbsPos() + ", length=" + this.getLength()  + "rangeMin=" + rangeMin + ", rangeMax="
						+ rangeMax + "]";// ", parentNode=" + this.getParentNode().toString()+ "]";
	}

}
