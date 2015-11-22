/* Kimberly Disher
 * CIS 35B
 * Updated for Lab 5, added fileExists()
 */
package util;

import java.io.*;
import java.util.*;
import automobile.*;
import exception.*;

public class FileIO {
	public FileIO() {}
	public Model buildAutoModelFromProperties(String fileName, Model automodel) throws AutoException{
		Properties autoProps = new Properties();
		FileInputStream autoIn = null;
		try {
			autoIn = new FileInputStream(fileName);
		}
		catch(FileNotFoundException e) {
			System.err.println("Properties file not found!");
		}
		try{
			autoProps.load(autoIn);
		}
		catch(IOException e){
			System.err.println("Error reading Properties input stream!");
		}
		parseProperties(autoProps, automodel);
		return automodel;
	}
	public Model buildAutoModelObject(String fileName, Model automodel) throws AutoException {
		boolean endFile = false;
		boolean autoExCalled = false;
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
						System.err.println("\nAutoException: " + ae.getErrMessage());
						line = ae.fixError();
						this.parseLine(automodel, line);
						autoExCalled = true;
					}
			}
			bReader.close();
			fReader.close();
			if (autoExCalled)
				this.writeCorrectedAutoFile(fileName, automodel);
			return automodel;
		}
		catch(Exception e) {
			System.err.println("Error reading model file: " + e.getMessage());
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
			System.out.println("Error reading Serialized objects: " + e.getMessage());
			return null;
		}
	}
	//default deserialize to "automodel.ser"
	public Model deserializeAutoModelObject(Model automodel) {return deserializeAutoModelObject("automodel.ser", automodel);}
	
	public void parseProperties (Properties autoProp, Model automodel) throws AutoException{
		String propName = "";
		String propVal;
		String optVal;
		double optPrice;
		int setcount = 1;
		int optcount;
		boolean hasSubs; //has option values left to go

		propVal = autoProp.getProperty("CarMake");
		if (propVal != null)	
			automodel.setMakerName(propVal);
		else throw new AutoException (102065, "CarMake not found.");
		propVal = autoProp.getProperty("CarModel");
		if (propVal != null)
			automodel.setModelName(propVal);
		else throw new AutoException (102065, "CarModel not found.");
		propVal = autoProp.getProperty("CarPrice");
		if (propVal != null)	
			automodel.setModelPrice(Double.parseDouble(propVal));
		else throw new AutoException (102065, "CarPrice not found.");
		
		do {
			propName = "Option" + setcount;
			propVal = autoProp.getProperty(propName);
			if (propVal != null) {
				automodel.addOptionSet(propVal, 2);
				hasSubs = true;
				optcount = 0;
				while(hasSubs) {
					propName = "OptionValue" + setcount + (char)('a' + optcount);
					optVal= autoProp.getProperty(propName);
					if (optVal!= null) {
						propName = "OptionPrice" + setcount + (char)('a' + optcount);
						if (autoProp.getProperty(propName) != null) {
							optPrice = Double.parseDouble(autoProp.getProperty(propName));
							automodel.addOptionToLastSet(optVal, optPrice);
						}
						else throw new AutoException (102065, propName + " not found.");
						optcount ++;
					}
					else hasSubs = false;
				}
			}
			setcount ++;
		}while (propVal != null);
	}
	public void parseLine(Model automodel, String line) throws AutoException {
		String [] splitLine = line.split(",");
		trimWhiteSpaceInArray(splitLine);

			if (splitLine[0].compareToIgnoreCase("model") == 0) {
				try {
				automodel.setModelName(splitLine[1]);
				}
				catch (IndexOutOfBoundsException ie) {
					throw new AutoException (102064, line);
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
				catch (NumberFormatException ne) { //number not an integer
					throw new AutoException (102063, line);
				}
				catch (IndexOutOfBoundsException ie) {	//number not there
					throw new AutoException(102063, line);
				}
			}
			else if (splitLine[0].compareToIgnoreCase("optionset") == 0) {
				if (splitLine.length < 3){	//missing something in the optionset line
					throw new AutoException (102061, line);
				}
				try {
					automodel.addOptionSet(splitLine[1], Integer.parseInt(splitLine[2]));
				}
				catch (NumberFormatException ne) { //number not there or not an integer
					throw new AutoException (102062, line);
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
					throw new AutoException (102062, line);
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
	//Save the operator'c corrective work for the automobile config file
	public void writeCorrectedAutoFile (String fileName, Model automodel) {
		String [] splitLine = new String [3];
		String file = "fixed" + fileName;
		try {
			PrintWriter pWriter = new PrintWriter(file);
			splitLine[0] = "Model";
			splitLine [1] = automodel.getModelName();
			splitLine [2] = "";
			pWriter.write(createLine(splitLine)); 
			splitLine[0] = "Model Price";
			splitLine[1] = Double.toString(automodel.getModelPrice());
			splitLine[2] = "";
			pWriter.write(createLine(splitLine));
			splitLine[0] = "OptionSets";
			splitLine[1] = Integer.toString(automodel.getOptionSetSize());
			splitLine[2] = "";
			pWriter.write(createLine(splitLine));
			for (int i = 0; i < automodel.getOptionSetSize(); ++i) {
				splitLine[0] = "OptionSet";
				splitLine[1] = automodel.getOptionSetName(i);
				splitLine[2] = Integer.toString(automodel.getOptionCount(i));	
				pWriter.write(createLine(splitLine));
				
				for (int j = 0; j < automodel.getOptionCount(i); ++j) {
					splitLine[0] = "Option";
					splitLine[1] = automodel.getOptionValue(i, j);
					splitLine[2] = Double.toString(automodel.getOptionPrice(i, j));
					pWriter.write(createLine(splitLine));
				}
 			}
			pWriter.close();
			System.out.println("\nFor your convenience, changes have been saved to " + file);
			System.out.println();
		}
		catch (Exception e) {} //file printed for convenience. not too worried if it does not print.
		
		
	}
	protected void trimWhiteSpaceInArray (String [] line){
		for (int i = 0; i < line.length; i++){
			line [i] = line[i].trim();
		}
	}
	private String createLine (String [] splitline) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < splitline.length; ++i) {
			if (splitline[i] != "") {
				sb.append(splitline[i]);
				sb.append(", ");
			}
		}
		sb.delete(sb.length() -2, sb.length());
		sb.append("\n");
		return sb.toString();
	}
	public static boolean fileExists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}
	public static boolean checkExtension(String fileName, String ext) {
		String [] strTempSplit  = fileName.split("\\.");
		boolean extMatch = false; //extension match
		if  (strTempSplit.length > 0 && strTempSplit[strTempSplit.length - 1].equalsIgnoreCase(ext)) {
			extMatch = true;
		}
		return extMatch;
	}
}
