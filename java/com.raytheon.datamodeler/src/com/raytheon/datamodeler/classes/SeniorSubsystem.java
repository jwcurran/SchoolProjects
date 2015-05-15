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
public class SeniorSubsystem extends SeniorNode {
	//private data members
	private String Endianness;
	//public methods
	public SeniorSubsystem(){
		super("Unknown Subsystem", new LinkedList<SeniorNode>() );
		setChildType("SeniorMessage");
		Endianness = "unknown endianness";
	}
	public SeniorSubsystem(String name,  LinkedList<? extends SeniorNode> children) {
		super(name,(LinkedList<SeniorNode>) children);
		setChildType("SeniorMessage");
		Endianness = "unkown endianness";
	}
	
	public String getEndianness() {
		return Endianness;
	}
	public void setEndianness(String endianness) {
		Endianness = endianness;
	}

}
