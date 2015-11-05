/* Kimberly Disher	
 * CIS 35B
 * New for Lab 4
 */
package scale;

import adapter.*;
public class EditOptions extends ProxyAuto implements ScaleThread { 
	String modName;
	String optSet;
	String oldVal;
	String newVal;
	boolean useWaitUpdate;
	
	public EditOptions (String [] args) {
		modName = args[0];
		optSet = args[1];
		oldVal = args[2];
		newVal = args[3];
		useWaitUpdate = Boolean.parseBoolean(args[4]); //returns false for anything other than true (case insensitve)
		
	}
	public void run() {
		if (useWaitUpdate){
			this.waitUpdateOptionValue(modName, optSet, oldVal, newVal);
		}
		else {
			this.updateOptionValue(modName, optSet, oldVal, newVal);
		}	
	}
}
