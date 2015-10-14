/* Kimberly Disher
 * CIS 35B
 * New for Lab 2
 */
package exception;
import java.util.*;

public class ErrorFix {
	private Scanner input;
	private static String DEFAULT_INPUT_STR = "automobile.txt";
	
	protected String fixInputFileString () {return DEFAULT_INPUT_STR;}
	
	protected String fixOptionSetLine(String line) {// code 102061
		String [] splitline = line.split(", ");
		String [] correctedsplit = new String [2];
		StringBuilder sb = new StringBuilder (splitline [0]);
		if ((splitline.length < 3) || !(splitline[2].matches("\\d+"))) {
			correctedsplit [1] = getCorrectedVal(line, ("\\d+"), "Option set not in expected format. Enter number of option values: ");
		}
		if (splitline.length < 2 || splitline[1].matches("\\d+")) {  //assuming here that an Option set name will not be numeric only
			correctedsplit [0] = this.getCorrectedVal(line, ".+", "Option set not in expected format. Enter the name for this option set: ");
		}
		else {
			correctedsplit[0] = splitline[1];
		}
	
		sb.append(", ");
		sb.append(correctedsplit[0]);
		sb.append(", ");
		sb.append(correctedsplit[1]);
		return sb.toString();
	}
	protected String fixOptionLine (String line) {
		String [] splitline = line.split(", ");
		String [] correctedsplit = new String [2];
		StringBuilder sb = new StringBuilder (splitline [0]);
		
		if ((splitline.length < 3) || !(splitline[2].matches("\\d+(\\.\\d{0,2})?"))) {
			correctedsplit [1] = getCorrectedVal(line, ("\\d+(\\.\\d{0,2})?"), "Line not in expected format. Enter option price: ");
		}
		if (splitline.length < 2 || splitline[1].matches("\\d+(\\.\\d{0,2})?")) {  //assuming here that an Option set name will not be numeric only
			correctedsplit [0] = this.getCorrectedVal(line, ".+", "Line not in expected format. Enter the name for this option: ");
		}
		else {
			correctedsplit[0] = splitline[1];
		}
	
		sb.append(", ");
		sb.append(correctedsplit[0]);
		sb.append(", ");
		sb.append(correctedsplit[1]);
		return sb.toString();
	}
	protected String fixNumber (String line) { //code 102061
		String [] splitline = line.split(", ");
		StringBuilder sb = new StringBuilder (splitline[0]);
		String correctedval = "";
		if (splitline[0].toLowerCase().matches(".+set")) { //option set
			correctedval = getCorrectedVal (line, "\\d+", "Enter a corrected count for this set of options: "); //regex catches a value with 1 or more digits
		}
		else {	//option
			correctedval = getCorrectedVal(line, "\\d+(\\.\\d{0,2})?", "Enter a corrected price for this option: "); //regex catches a value with a 0-2 digits after the decimal place
		}
		sb.append(", ");
		sb.append(splitline[1]);
		sb.append(", ");
		sb.append(correctedval);
		
		return sb.toString();
	}
	protected String fixOptionSetCount (String line) { //code 102062
		StringBuilder correctedLine = new StringBuilder (line.split(", ")[0]);	//dump the end of the line
		correctedLine.append(", ");
		correctedLine.append(getCorrectedVal(line, "\\d+", "Enter a number for the count of option sets: "));
		return correctedLine.toString();
		 
	}
	protected String fixModelName (String line) { //code 102063
		StringBuilder correctedLine = new StringBuilder (line.split(", ")[0]);	//dump the end of the line
		correctedLine.append(", ");
		correctedLine.append(getCorrectedVal(line, ".+", "Enter a new for this automobile model: "));
		return correctedLine.toString();
	}
	private String getCorrectedVal(String line, String regex, String query) {
		String correctedval = "";
		boolean goodInput = false;
		if (input == null){
			input = new Scanner (System.in);
		}
		System.out.print ("Line With a problem: \"");
		System.out.print (line);
		System.out.println("\"");
		
		while (!goodInput) {
			
			System.out.print (query);
			correctedval = input.next();
			input.nextLine();
			if (correctedval.matches(regex)) {
				goodInput = true;
			}
			else {
				System.out.println ("Please try again.");
			}
		}
		return correctedval;
		
	}
	
}
