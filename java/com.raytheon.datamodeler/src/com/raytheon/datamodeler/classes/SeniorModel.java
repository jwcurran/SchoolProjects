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
public class SeniorModel extends SeniorNode {
	
	public SeniorModel(){
		super("Unknown Model", new LinkedList<SeniorNode>() );
		setChildType("SeniorDomain");
	}
	public SeniorModel(String name,  LinkedList<? extends SeniorNode> children) {
		super(name,(LinkedList<SeniorNode>) children);
		setChildType("SeniorDomain");
	}
	
}
