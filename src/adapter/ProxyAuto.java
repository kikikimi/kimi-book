/* Kimberly Disher
 * CIS 35B
 * Updated for Lab 3
 */
package adapter;

import automobile.*;
import util.*;
import exception.*;
import java.util.*;

public abstract class ProxyAuto {
	private Model _autoModel;
	private static LinkedHashMap <String, Model> _autoModelGroup = new LinkedHashMap <String, Model> ();
	
	public void buildAuto(String fileName) { 
		FileIO builder = new FileIO ();
		_autoModel = new Model ();
			try {

				_autoModel = builder.buildAutoModelObject(fileName, _autoModel);
			}
			catch (AutoException ae){
				System.out.print (ae.getErrMessage());
				try {
					_autoModel = builder.buildAutoModelObject(fileName, _autoModel);
				}
				catch (AutoException e){
					System.out.print ("Could not resolve error " + ae.getErrMessage());
				}
			}
			finally {
				if (_autoModel != null) {
					_autoModel.setDefaultOptionChoices();
					addAuto(_autoModel);
				}
			}
	}
	public void fixModel() {/*we'll put user-interaction or pre-determined business rule action in here later*/}
	
	//prints to console based on if the model name is found
	public void printAuto(String modelName) {
		_autoModel = _autoModelGroup.get(modelName);
		if (_autoModel != null) {
			System.out.println(_autoModel.toString());
			System.out.print("Price with selected options(may be defaults):");
			System.out.println(_autoModel.getTotalPrice());
		}
		else System.out.print("Model name" + modelName + "not found");
	}
	//optionName goes with optionset, optVal is the String property in an option.
	public void updateOptionPrice(String modelName, String optionName, String optVal, float newprice) {
		int setIndex;
		_autoModel = _autoModelGroup.get(modelName);
		if (_autoModel != null) {
			setIndex = _autoModel.findOptionSetIndex(optionName);
			if (setIndex != -1) {
				_autoModel.updateOptionPrice (setIndex, optVal, newprice);
			}
		}
	}
	public void updateOptionSetName(String modelName, String optionSetName, String newName) { 
		int setIndex;
		_autoModel = _autoModelGroup.get(modelName);
		if (_autoModel != null) {
			setIndex = _autoModel.findOptionSetIndex(optionSetName);
			if (setIndex != -1) {
				_autoModel.updateOptionSetName (setIndex, newName);
			}
		}
	}
	//optionName goes with optionset, optVal is the String property in an option.
	public void updateOptionValue(String modelName, String optionName, String oldOptVal, String newOptVal){
		int setIndex;
		_autoModel = _autoModelGroup.get(modelName);
		if (_autoModel != null) {
			setIndex = _autoModel.findOptionSetIndex(optionName);
			if (setIndex != -1) {
				_autoModel.updateOptionName(setIndex, oldOptVal, newOptVal);
			}
		}
	}
	//this will remove a Model from the _autoModelGroup
	public void removeAuto (String modelName) {	
		_autoModelGroup.remove(modelName);
	}
	public void addAuto(Model autoModel) {
		if (!_autoModelGroup.containsKey(autoModel.getModelName())) {
			_autoModelGroup.put(autoModel.getModelName(), autoModel);
		}
	}
}
