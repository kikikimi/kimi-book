/* Kimberly Disher
 * CIS 35B
 * New for Lab 5
 */
package client;

import java.util.*;
import automobile.Model;
import util.FileIO;

public class SelectCarModelOption {

	private Scanner localInput = null;
	
	public void displayList (ArrayList <String> names) {
		for (int i = 0; i < names.size(); i++) {
			System.out.print ((i + 1) + ". ");
			System.out.println (names.get(i));
		}
	}
	public void displayConfiguredAuto (String configuredAuto) {
		System.out.println (configuredAuto);
	}
	public void displayMenu () {
		System.out.println("Auto Model Configuration Menu\n");
		System.out.println ("1. Upload a model file.");
		System.out.println ("2. Configure a model's options.");
		System.out.println ("3. Exit\n");
	}
	public String getPropertiesFileName () {
		String fileNm = "";
		boolean goodInput = false;
		if (localInput == null)
			localInput = new Scanner(System.in);
		while (!goodInput) {
			System.out.print ("Auto Properties file to upload: ");
			fileNm = localInput.next();
			if ((FileIO.fileExists(fileNm)) && (FileIO.checkExtension(fileNm, "properties")))
				goodInput = true;
			else {
				System.out.println();
				System.out.print("Please enter a .properties file");
			}
		}
		return fileNm;
	}
	public String getCmd () {
		String cmd = "";
		boolean goodInput = false;
		if (localInput == null)
			localInput = new Scanner(System.in);
		while (!goodInput) {
			System.out.print ("Choice: ");
			cmd = localInput.next();
			if (cmd.matches("[1-3]")) 
				goodInput = true;
			else {
				System.out.println();
				System.out.print("Enter only 1-3. ");
			}
		}
		switch (cmd) {
			case "1" : cmd =  "UPLOAD";
				break;
			case "2" : cmd = "CONFIGURE";
				break;
			case "3" : cmd = "QUIT";
		}
		return cmd;
	}
	public int promptInput (String listDesc, int start, int end) {
		boolean goodInput = false;
		String strIndex= ""; //string to contain a number, will be checked for type.
		int index = 0; //selected index in the ArrayList names
		
		if (localInput == null){
			localInput = new Scanner(System.in);
		}
		while (!goodInput) {
			System.out.print("Select a ");
			System.out.print(listDesc);
			System.out.print(" by number:");
			strIndex = localInput.next();
			try {
				index = Integer.parseInt(strIndex);
				if (index >= start && index <= end)
					goodInput = true;
			}
			catch (NumberFormatException e) {}
			if (!goodInput) {
				System.out.print("Enter only ");
				System.out.print(start + "-");
				System.out.print(end + ". ");
			}
		} 
		return index;
	}
	public void configureAuto (Model car) {
		System.out.println ("\n\n");
		ArrayList<String>optSetNames = car.getAllOptSetNames();
		int optIndex = 1;
		while (optIndex > 0) {
			System.out.println("\nSelect an Option to configure or 0 to quit.");
			displayList(optSetNames);
			optIndex = this.promptInput("option for " + car.getModelName(), 0, optSetNames.size());
			if (optIndex < 1) {
				break;
			}
			else {
				car = configureOption(car, optIndex - 1);
			}
		}
		System.out.println();
		this.displayConfiguredAuto(car.toStringWChoices(false));
		System.out.println();
	}
	public Model configureOption (Model car, int optSetIndex) {
		System.out.println ();
		System.out.println (car.getOptionSetName(optSetIndex));
		int index;
		int selectedIndex;
		for (index = 0; index < car.getOptionCount(optSetIndex); index++) {
			System.out.print((index+1));
			System.out.print(". ");		
			System.out.print(car.getOptionValue(optSetIndex, index));
			System.out.println();
		}
		selectedIndex = this.promptInput("option for "+ car.getOptionSetName(optSetIndex), 1, index) - 1;
		car.setOptionChoice(optSetIndex, selectedIndex);
		return car;
	}
}
