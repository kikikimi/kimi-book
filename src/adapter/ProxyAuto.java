/* Kimberly Disher
 * CIS 35B
 * New for Lab 2
 */
package adapter;

import automobile.*;
import util.*;
import exception.*;

public abstract class ProxyAuto {
	private static Model _autoModel;
	
	public void buildAuto(String fileName) { 
		FileIO builder = new FileIO ();
			try {
				_autoModel = builder.buildAutoModelObject(fileName, _autoModel);
			}
			catch (AutoException ae){
				System.out.print (ae.getErrMessage());
				try {
					_autoModel = builder.buildAutoModelObject(ae.fixError(),  _autoModel);
				}
				catch (AutoException e){
					System.out.print ("Could not resolve error " + ae.getErrMessage());
				}
			}
			finally {
				
			}
	}
	public void fixModel() {/*we'll put user-interaction or pre-determined business rule action in here later*/}
	
	//prints to console based on if the model name is found
	public void printAuto(String modelName) {
		if (compareNames (_autoModel.getModelName(), modelName))
			System.out.print(_autoModel.toString());
		else System.out.print("Model name" + modelName + "not found");
	}
	//optionName goes with optionset, optVal is the String property in an option.
	public void updateOptionPrice(String modelName, String optionName, String optVal, float newprice) {
		int setIndex;
		
		if (compareNames (_autoModel.getModelName(), modelName)) {
			setIndex = _autoModel.findOptionSetIndex(optionName);
			if (setIndex != -1) {
				_autoModel.updateOptionPrice (setIndex, optVal, newprice);
			}
		}
	}
	public void updateOptionSetName(String modelName, String optionSetName, String newName) { 
		int setIndex;
		
		if (compareNames (_autoModel.getModelName(), modelName)) {
			setIndex = _autoModel.findOptionSetIndex(optionSetName);
			if (setIndex != -1) {
				_autoModel.updateOptionSetName (setIndex, newName);
			}
		}
	}
	//optionName goes with optionset, optVal is the String property in an option.
	public void updateOptionValue(String modelName, String optionName, String oldOptVal, String newOptVal){
		int setIndex;
		if (compareNames (_autoModel.getModelName(), modelName)) {
			setIndex = _autoModel.findOptionSetIndex(optionName);
			if (setIndex != -1) {
				_autoModel.updateOptionName(setIndex, oldOptVal, newOptVal);
			}
		}
	}
	//helper. This may be modified in the future to become findModel ().
	private static boolean compareNames (String existingName, String queryName)
	{
		String existingLowercase = existingName.toLowerCase();
		String queryLowercase = queryName.toLowerCase();
		if (existingLowercase.indexOf(queryLowercase) != -1)
			return true;
		else return false;
	}
}
