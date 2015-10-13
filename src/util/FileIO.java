/* Kimberly Disher
 * CIS 35B Lab 1
 */
package util;

import java.io.*;

import automobile.*;
import exception.*;

import java.util.*;

public class FileIO {
	public FileIO() {}
	
	public Model buildAutoModelObject(String fileName, Model automodel) throws AutoException {
		boolean endFile = false;
		String line = "";
		File fileCheck = new File (fileName);
		System.out.println("Trying to load " + fileCheck.getAbsolutePath());
		if (!fileCheck.exists()) {
			throw new AutoException (10404, fileCheck.getAbsolutePath());
		}
		
		try {
			FileReader fReader = new FileReader(fileName);
			BufferedReader bReader = new BufferedReader(fReader);
			
			while (!endFile)
			{
				line = bReader.readLine();
				if (line == null) 
					endFile = true;
				else 
					try {
						parseLine(automodel, line);
					}
					catch (AutoException ae) {
						line = ae.fixError();
						this.parseLine(automodel, line);
					}
			}
			bReader.close();
			fReader.close();
			return automodel;
		}
		catch(Exception e) {
			System.out.println("Error reading model file: " + e.getMessage());
			return null;
		}
	}
	public Model deserializeAutoModelObject(String fileName, Model automodel) {
		try{
			ObjectInputStream objectIn = new ObjectInputStream (new FileInputStream (fileName));
			automodel = (Model) objectIn.readObject();
			objectIn.close();
			return automodel;
		}
		catch (Exception e){
			System.out.println("Error writing Serialized objects: " + e.getMessage());
			return null;
		}
	}
	//default deserialize to "automodel.ser"
	public Model deserializeAutoModelObject(Model automodel) {return deserializeAutoModelObject("automodel.ser", automodel);}
	
	public void parseLine(Model automodel, String line) throws AutoException {
		String [] splitLine = line.split(",");
		trimWhiteSpaceInArray(splitLine);

			if (splitLine[0].compareToIgnoreCase("model") == 0) {
				try {
				automodel.setModelName(splitLine[1]);
				}
				catch (IndexOutOfBoundsException ie) {
					throw new AutoException (102063, line);
				}
			}
			else if (splitLine[0].compareToIgnoreCase("model price") == 0) {
				automodel.setModelPrice(Double.parseDouble(splitLine[1]));
			}
			else if (splitLine[0].compareToIgnoreCase("optionsets") == 0 || 
					splitLine[0].compareToIgnoreCase("option sets") == 0) {
				try {
					automodel.initOptionSets(Integer.parseInt(splitLine[1]));
				}
				catch (NumberFormatException ne) { //number not there or not an integer
					throw new AutoException (102062, line);
				}
			}
			else if (splitLine[0].compareToIgnoreCase("optionset") == 0) {
				if (splitLine.length < 3){	//missing something in the optionset line
					throw new AutoException (10206, line);
				}
				try {
					automodel.addOptionSet(splitLine[1], Integer.parseInt(splitLine[2]));
				}
				catch (NumberFormatException ne) { //number not there or not an integer
					throw new AutoException (102061, line);
				}
			}
			else if (splitLine[0].compareToIgnoreCase("option") == 0){
				if (splitLine.length < 3){	//missing something in the option line
					throw new AutoException (10206, line);
				}
				try {
					automodel.addOptionToLastSet(splitLine[1], Double.parseDouble(splitLine[2]));
				}
				catch (NumberFormatException ne) {
					throw new AutoException (102061, line);
				}
			}
	}
	public boolean serializeAutoObject(String fileName, Model automodel) {
		try{
			ObjectOutputStream objectOut = new ObjectOutputStream (new FileOutputStream (fileName));
			objectOut.writeObject(automodel);
			objectOut.close();
		return true;
		}
		catch (Exception e){
			System.out.println ("Error loading Serialized objects: " + e.getMessage());
			return false;
		}
	}
	protected void trimWhiteSpaceInArray (String [] line){
		for (int i = 0; i < line.length; i++){
			line [i] = line[i].trim();
		}
	}
	private int getNumberFromConsole(String query){
		Scanner in = new Scanner (System.in);
		boolean goodInput = false;
		String enteredVal = "";
		
		while (!goodInput) {
			System.out.print (query);
			enteredVal = in.nextLine();
			if (enteredVal.matches("/d+")) {
				goodInput = true;
			}
			else {
				System.out.println ("Please enter only a number.");
			}
		}
		in.close();
		return Integer.parseInt(enteredVal);
	}
}
