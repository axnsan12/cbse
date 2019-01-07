package main;

import java.io.*;
import mainframe.MainFrame;
import minijavaparser.*;
import progen.CD05Visitor;

public class MainClass 
{
	public static void main(String args[]) 
	{
	    System.out.println("Reading from standard input...");
	    try 
	    {
	      	MiniJava p = new MiniJava(new FileInputStream(new File("./samples/test03.java")));
	      	p.setTabSize(4);
	    	ASTProgram root = p.Program();
	    	CD05Visitor visitor = new CD05Visitor();
	    	visitor.visit(root, null);
	    	//root.dump(">");

			MainFrame frame=new MainFrame(root);
			frame.setVisible(true);			
			
			System.out.println("Thank you.");
	    } 
	    catch(Exception e)
	    {									      
			System.err.println("Oops.");
			System.err.println(e.getMessage());
			e.printStackTrace();
	    }
	}
}
