package main;

import java.io.*;
import mainframe.MainFrame;
import minijavaparser.*;

public class MainClass 
{
	public static void main(String args[]) 
	{
	    System.out.println("Reading from standard input...");
	    try 
	    {
	      	MiniJava p = new MiniJava(new FileInputStream(new File("./samples/test01.java")));
	    	ASTProgram root = p.Program();
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
