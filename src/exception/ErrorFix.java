/* Kimberly Disher
 * CIS 35B
 * New for Lab 2
 */
package exception;

public class ErrorFix {
	private static String DEFAULT_INPUT_STR = "automobile.txt";
	private static int DEFAULT_OPTION_COUNT= 20;
	protected String fixInputFileString () {
		return DEFAULT_INPUT_STR;
	}
	protected String fixOptionCountLine (String line) {
		StringBuilder sb = new StringBuilder (line);
		sb.append(", ");
		sb.append(DEFAULT_OPTION_COUNT);
		return sb.toString();
	}
}
