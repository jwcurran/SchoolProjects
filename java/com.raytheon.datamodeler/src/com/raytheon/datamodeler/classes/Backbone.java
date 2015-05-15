package com.raytheon.datamodeler.classes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;






public class Backbone {
		
	
	public static void main(String[] args) {
		
		//Class Test
		
		//field
		SeniorField fieldEmptyTest = new SeniorField();
		SeniorField fieldFullTest = new SeniorField("full_Constructor",1,"int", 1,0,8);

		System.out.println("Empty Field Constructor Test\n" + fieldEmptyTest.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Full Consructor Test\n" + fieldFullTest.getTestString() );
		
		fieldEmptyTest.setName("Setter Empty");
		fieldEmptyTest.setDataType("int");
		//fieldEmptyTest.setIndex(0);
		fieldEmptyTest.setWordPos(0);
		fieldEmptyTest.setBitPos(0);
		fieldEmptyTest.setLength(8);
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Setter Field Test\n" + fieldEmptyTest.getTestString() );
		
		
		System.out.println("______________________________________________________________________");
		
		//numeric field
//		SeniorNumericField numberEmptyTest = new SeniorNumericField();
//		SeniorNumericField numberFullTest = new SeniorNumericField("full_Constructor",1,"int", 1,0,8,0,10);
//		
//		System.out.println("Empty NumberField Constructor Test\n" + numberEmptyTest.getTestString() );
//		System.out.println("---------------------------------------------------------------------");
//		System.out.println("Full NumberField Consructor Test\n" + numberFullTest.getTestString() );
//		numberEmptyTest.setNumber(true);
//		numberEmptyTest.setRangeMin(0);
//		numberEmptyTest.setRangeMax(20);
//		System.out.println("---------------------------------------------------------------------");
//		System.out.println("Setter NumberField Test\n" + numberEmptyTest.getTestString() );
//		
//		System.out.println("______________________________________________________________________");
		
		//Protocol
//		SeniorProtocol EmptyProtocolTest = new SeniorProtocol();
//		
//		LinkedList<SeniorField> testFieldLL = new LinkedList<SeniorField>();
//		testFieldLL.add(fieldEmptyTest);
//		testFieldLL.add(numberEmptyTest);
//		
//		SeniorProtocol FullProtocoltest = new SeniorProtocol("Full Constructor Procol", testFieldLL );
//		
//		System.out.println("Empty Protocol Constructor Test\n" + EmptyProtocolTest.getTestString() );
//		System.out.println("---------------------------------------------------------------------");
//		System.out.println("Adding field Test");
//		EmptyProtocolTest.addChild(fieldEmptyTest);
//		System.out.println("New empty Protocol is\n" + EmptyProtocolTest.getTestString() );
//		System.out.println("---------------------------------------------------------------------");
//		System.out.println("Full Protocol Consructor Test\n" + FullProtocoltest.getTestString() );
		
		
		
		System.out.println("______________________________________________________________________");
		
		//Message
		
		SeniorMessage emptyMessage = new SeniorMessage();
		LinkedList<SeniorField> fieldTestLL = new LinkedList<SeniorField>();
		fieldTestLL.add(fieldEmptyTest);
		fieldTestLL.add(fieldFullTest);
		SeniorMessage fullMessage = new SeniorMessage("Full Message",fieldTestLL);
		
		System.out.println("Empty Message Constructor Test\n" + emptyMessage.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Adding Protocol Test");
		emptyMessage.addChild(fieldEmptyTest);
		System.out.println("New empty Message is\n" + emptyMessage.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Full Message Consructor Test\n" + fullMessage.getTestString() );
		
		System.out.println("______________________________________________________________________");
		
		//Subsystem
		
		SeniorSubsystem emptySubsystem = new SeniorSubsystem();
		LinkedList<SeniorMessage> MessageTestLL = new LinkedList<SeniorMessage>();
		MessageTestLL.add(emptyMessage);
		MessageTestLL.add(fullMessage);
		SeniorSubsystem fullSubsystem = new SeniorSubsystem("Full Subsystem",MessageTestLL);
		
		System.out.println("Empty Subsystem Constructor Test\n" + emptySubsystem.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Adding Message Test");
		emptySubsystem.addChild(emptyMessage);
		System.out.println("New empty subsystem is\n" + emptySubsystem.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Full Subsystem Consructor Test\n" + fullSubsystem.getTestString() );
		
		System.out.println("______________________________________________________________________");
		
		//System
		
		SeniorSystem emptySystem = new SeniorSystem();
		LinkedList<SeniorSubsystem> subsystemTestLL = new LinkedList<SeniorSubsystem>();
		subsystemTestLL.add(emptySubsystem);
		subsystemTestLL.add(fullSubsystem);
		SeniorSystem fullSystem = new SeniorSystem("Full System",subsystemTestLL);
		
		System.out.println("Empty System Constructor Test\n" + emptySystem.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Adding Subsystem Test");
		emptySystem.addChild(emptySubsystem);
		System.out.println("New empty System is\n" + emptySystem.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Full System Consructor Test\n" + fullSystem.getTestString() );
		
		System.out.println("______________________________________________________________________");
		
		//Domain
		
		SeniorDomain emptyDomain = new SeniorDomain();
		LinkedList<SeniorSystem> systemTestLL = new LinkedList<SeniorSystem>();
		systemTestLL.add(emptySystem);
		systemTestLL.add(fullSystem);
		SeniorDomain fullDomain = new SeniorDomain("Full Domain",systemTestLL);
		
		System.out.println("Empty Domain Constructor Test\n" + emptyDomain.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Adding System Test");
		emptyDomain.addChild(emptySystem);
		System.out.println("New empty Domain is\n" + emptyDomain.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Full Domain Consructor Test\n" + fullDomain.getTestString() );
		
		System.out.println("______________________________________________________________________");
		
		//Model
		
		SeniorModel emptyModel = new SeniorModel();
		LinkedList<SeniorDomain> domainTestLL = new LinkedList<SeniorDomain>();
		domainTestLL.add(emptyDomain);
		domainTestLL.add(fullDomain);
		SeniorModel fullModel = new SeniorModel("Full Model",domainTestLL);
		
		System.out.println("Empty Model Constructor Test\n" + emptyModel.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Adding Domain Test");
		emptyModel.addChild(emptyDomain);
		System.out.println("New empty Model is\n" + emptyModel.getTestString() );
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Full Model Consructor Test\n" + fullModel.getTestString() );
		//*****************End of class Test
		System.out.println("______________________________________________________________________");
		System.out.println("Reflection Testing");
		//Reflection Test
		SeniorField reflectionTestField = fieldEmptyTest;
		SeniorNode reflectionTestNode = (SeniorNode)reflectionTestField;
		
		System.out.println("These Should both say SeniorField\n" + reflectionTestField.getClass().getSimpleName() +" = "+ reflectionTestNode.getClass().getSimpleName() ); 
		
		//XML Test
		//Marshaling
		System.out.println("______________________________________________________________________");
		System.out.println("XML Testing");
		
		try {
			System.out.println("The XML for TopTest is:");
			System.out.println(fullModel.getXMLString());
			PrintWriter pwriter = new PrintWriter( "testfile.xml" , "UTF-8");
			pwriter.write( fullModel.getXMLString() );
			pwriter.close();
		
		
		// Un-marshaling
//			String xml = readFile("testfile.xml");
//			System.out.println("The XML read is:\n" + xml);
			System.out.println("The Object Parsed is:");
			SeniorModel fromXML = (SeniorModel) SeniorModel.getObjectFromXML("testfile.xml");
			//System.out.println( fromXML.getTestString() );
			System.out.println( fromXML.getRecursiveTestString() );
		  }
		
		   catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		finally{
			
		}

		//*****************Get Parent Test************************************************************
		System.out.println("______________________________________________________________________");
		System.out.println("Get Parent Test");
		SeniorNode fieldParent;
		fieldParent = fullModel.getParentNode(fieldFullTest);
		System.out.println("Looking for " + fieldFullTest.getTestString() );
		if(fieldParent != null ){
			System.out.println( fieldParent.getTestString() );
		}
		
		//*****************Field Ordered Insert and ReOrdering Test**************************************
//		System.out.println("______________________________________________________________________");
//		System.out.println("Abs Generation Test____________________________________________________");
//		SeniorProtocol ProtocolLocationTest = new SeniorProtocol();
//		
//		SeniorField fieldLocationTest1 = new SeniorField();
//		fieldLocationTest1.setNumber(true);
//		fieldLocationTest1.setWordPos(0);
//		fieldLocationTest1.setBitPos(2);
//		fieldLocationTest1.setLength(1);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest1 );
//		System.out.println("Test1's abs should be 2 it is " + fieldLocationTest1.getAbsPos() + " " + fieldLocationTest1.toString() );
//		
//		SeniorField fieldLocationTest2 = new SeniorField();
//		fieldLocationTest2.setNumber(true);
//		fieldLocationTest2.setWordPos(0);
//		fieldLocationTest2.setBitPos(7);
//		fieldLocationTest2.setLength(3);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest2 );
//		System.out.println("Test2's abs should be 7 it is " + fieldLocationTest2.getAbsPos() + " " + fieldLocationTest2.toString() );
//		
//		SeniorField fieldLocationTest3 = new SeniorField();
//		fieldLocationTest3.setNumber(true);
//		fieldLocationTest3.setWordPos(1);
//		fieldLocationTest3.setBitPos(4);
//		fieldLocationTest3.setLength(2);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest3 );
//		System.out.println("Test3's abs should be 12 it is " + fieldLocationTest3.getAbsPos() + " " + fieldLocationTest3.toString() );
//		
//		SeniorField fieldLocationTest4 = new SeniorField();
//		fieldLocationTest4.setNumber(true);
//		fieldLocationTest4.setWordPos(0);
//		fieldLocationTest4.setBitPos(4);
//		fieldLocationTest4.setLength(1);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest4 );
//		System.out.println("Test4's abs should be 4 it is " + fieldLocationTest4.getAbsPos() + " " + fieldLocationTest4.toString() );
//		
//		SeniorField fieldLocationTest5 = new SeniorField();
//		fieldLocationTest5.setNumber(true);
//		fieldLocationTest5.setWordPos(0);
//		fieldLocationTest5.setBitPos(0);
//		fieldLocationTest5.setLength(1);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest5 );
//		System.out.println("Test5's abs should be 0 it is " + fieldLocationTest5.getAbsPos() + " " + fieldLocationTest5.toString() );
//		
//		SeniorField fieldLocationTest6 = new SeniorField();
//		fieldLocationTest6.setNumber(true);
//		fieldLocationTest6.setWordPos(1);
//		fieldLocationTest6.setBitPos(3);
//		fieldLocationTest6.setLength(5);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest6 );
//		System.out.println("Test6's abs should be 11 it is " + fieldLocationTest6.getAbsPos() + " " + fieldLocationTest6.toString() );
//		
//		SeniorField fieldLocationTest7 = new SeniorField();
//		fieldLocationTest7.setNumber(true);
//		fieldLocationTest7.setWordPos(0);
//		fieldLocationTest7.setBitPos(1);
//		fieldLocationTest7.setLength(1);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest7 );
//		System.out.println("Test7's abs should be 1 it is " + fieldLocationTest7.getAbsPos() + " " + fieldLocationTest7.toString() );
//		
//		SeniorField fieldLocationTest8 = new SeniorField();
//		fieldLocationTest8.setNumber(true);
//		fieldLocationTest8.setWordPos(0);
//		fieldLocationTest8.setBitPos(5);
//		fieldLocationTest8.setLength(3);
//		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest8 );
//		System.out.println("Test8's abs should be 5 it is " + fieldLocationTest8.getAbsPos() + " " + fieldLocationTest8.toString() );
//		
//		System.out.println("Ordered Add Test_________________________________________________");
//		System.out.println("Should be 5 7 1 4 2 3 \n" +
//		
//		ProtocolLocationTest.getChildren().toString() );
//		
//		
//		System.out.println("Move Up Test");
//		
//		ProtocolLocationTest.moveChildUp(fieldLocationTest5);
//		System.out.println("Should be 5 7 1 4 2 3 \n" +
//		ProtocolLocationTest.getChildren().toString() );
//		
//		ProtocolLocationTest.moveChildUp(fieldLocationTest2);
//		System.out.println("Should be 5 7 1 2 4 3 \n" +
//		ProtocolLocationTest.getChildren().toString() );
//		
//		System.out.println("Move Down Test");
//		
//		ProtocolLocationTest.moveChildDown(fieldLocationTest3);
//		System.out.println("Should be 5 7 1 2 4 3 \n" +
//		ProtocolLocationTest.getChildren().toString() );
//		
//		ProtocolLocationTest.moveChildDown(fieldLocationTest1);
//		System.out.println("Should be 5 7 2 1 4 3 \n" +
//		ProtocolLocationTest.getChildren().toString() );
		
		System.out.println("______________________________________________________________________");
		System.out.println("Abs Generation Test____________________________________________________");
		SeniorMessage ProtocolLocationTest = new SeniorMessage();
		
		SeniorField fieldLocationTest1 = new SeniorField();
		fieldLocationTest1.setNumber(true);
		fieldLocationTest1.setWordPos(0);
		fieldLocationTest1.setBitPos(2);
		fieldLocationTest1.setLength(1);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest1 );
		System.out.println("Test1's abs should be 2 it is " + fieldLocationTest1.getAbsPos() + " " + fieldLocationTest1.toString() );
		
		SeniorField fieldLocationTest2 = new SeniorField();
		fieldLocationTest2.setNumber(true);
		fieldLocationTest2.setWordPos(0);
		fieldLocationTest2.setBitPos(7);
		fieldLocationTest2.setLength(3);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest2 );
		System.out.println("Test2's abs should be 7 it is " + fieldLocationTest2.getAbsPos() + " " + fieldLocationTest2.toString() );
		
		SeniorField fieldLocationTest3 = new SeniorField();
		fieldLocationTest3.setNumber(true);
		fieldLocationTest3.setWordPos(1);
		fieldLocationTest3.setBitPos(4);
		fieldLocationTest3.setLength(2);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest3 );
		System.out.println("Test3's abs should be 12 it is " + fieldLocationTest3.getAbsPos() + " " + fieldLocationTest3.toString() );
		
		SeniorField fieldLocationTest4 = new SeniorField();
		fieldLocationTest4.setNumber(true);
		fieldLocationTest4.setWordPos(0);
		fieldLocationTest4.setBitPos(4);
		fieldLocationTest4.setLength(1);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest4 );
		System.out.println("Test4's abs should be 4 it is " + fieldLocationTest4.getAbsPos() + " " + fieldLocationTest4.toString() );
		
		SeniorField fieldLocationTest5 = new SeniorField();
		fieldLocationTest5.setNumber(true);
		fieldLocationTest5.setWordPos(0);
		fieldLocationTest5.setBitPos(0);
		fieldLocationTest5.setLength(1);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest5 );
		System.out.println("Test5's abs should be 0 it is " + fieldLocationTest5.getAbsPos() + " " + fieldLocationTest5.toString() );
		
		SeniorField fieldLocationTest6 = new SeniorField();
		fieldLocationTest6.setNumber(true);
		fieldLocationTest6.setWordPos(1);
		fieldLocationTest6.setBitPos(3);
		fieldLocationTest6.setLength(5);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest6 );
		System.out.println("Test6's abs should be 11 it is " + fieldLocationTest6.getAbsPos() + " " + fieldLocationTest6.toString() );
		
		SeniorField fieldLocationTest7 = new SeniorField();
		fieldLocationTest7.setNumber(true);
		fieldLocationTest7.setWordPos(0);
		fieldLocationTest7.setBitPos(1);
		fieldLocationTest7.setLength(1);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest7 );
		System.out.println("Test7's abs should be 1 it is " + fieldLocationTest7.getAbsPos() + " " + fieldLocationTest7.toString() );
		
		SeniorField fieldLocationTest8 = new SeniorField();
		fieldLocationTest8.setNumber(true);
		fieldLocationTest8.setWordPos(0);
		fieldLocationTest8.setBitPos(5);
		fieldLocationTest8.setLength(3);
		ProtocolLocationTest.addChild( (SeniorNode) fieldLocationTest8 );
		System.out.println("Test8's abs should be 5 it is " + fieldLocationTest8.getAbsPos() + " " + fieldLocationTest8.toString() );
		
		System.out.println("Ordered Add Test_________________________________________________");
		System.out.println("Should be 5 7 1 4 2 3 \n" +
		
		ProtocolLocationTest.getChildren().toString() );
		
		
		System.out.println("Move Up Test");
		
		ProtocolLocationTest.moveChildUp(fieldLocationTest5);
		System.out.println("Should be 5 7 1 4 2 3 \n" +
		ProtocolLocationTest.getChildren().toString() );
		
		ProtocolLocationTest.moveChildUp(fieldLocationTest2);
		System.out.println("Should be 5 7 1 2 4 3 \n" +
		ProtocolLocationTest.getChildren().toString() );
		
		System.out.println("Move Down Test");
		
		ProtocolLocationTest.moveChildDown(fieldLocationTest3);
		System.out.println("Should be 5 7 1 2 4 3 \n" +
		ProtocolLocationTest.getChildren().toString() );
		
		ProtocolLocationTest.moveChildDown(fieldLocationTest1);
		System.out.println("Should be 5 7 2 1 4 3 \n" +
		ProtocolLocationTest.getChildren().toString() );
		
	}

}
