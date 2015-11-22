/* Kimberly Disher
 * CIS 35B
 * Updated for Lab 5, added a buildAuto() to accept properties objects
 */
package adapter;

import automobile.*;
import util.*;
import exception.*;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.*;

public abstract class ProxyAuto {
	private Model _autoModel;
	private static LinkedHashMap <String, Model> _autoModelGroup = new LinkedHashMap <String, Model> ();
	
	public void buildAuto(String fileName, String fileType) { 
		FileIO builder = new FileIO ();
		_autoModel = new Model ();
			if (fileType.indexOf("prop") > -1) {
				try{
					_autoModel = builder.buildAutoModelFromProperties(fileName, _autoModel);
				}
				catch (AutoException ae){
					System.out.print (ae.getErrMessage());
				}
			}
			else {
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
			}
			if (_autoModel != null) {
				_autoModel.setDefaultOptionChoices();
				addAuto(_autoModel);
			}
	}
	public boolean buildAuto(Properties autoProp) {
		FileIO builder = new FileIO ();
		_autoModel = new Model ();
		try{
			builder.parseProperties(autoProp, _autoModel);
		}
		catch (AutoException e) {
			System.err.println(e.getMessage());
			if (e.getErrCode() == 102065) //missing properties, don't continue with this file.
				_autoModel = null;
		} 
		
		if (_autoModel != null) {
			_autoModel.setDefaultOptionChoices();
			addAuto(_autoModel);
			return this.isAutoHere(_autoModel.getModelName());
		}
		else return false;
	}
	public void fixModel() {/*we'll put user-interaction or pre-determined business rule action in here later*/}
	
	//prints to console based on if the model name is found
	public void printAuto(String modelName) {
		_autoModel = _autoModelGroup.get(modelName);
		if (_autoModel != null) {
			//using toStringWChoices to show optionChoice is functional
			System.out.println(_autoModel.toStringWChoices(true));
			System.out.print("Price with selected options(may be defaults):");
			System.out.println(_autoModel.getTotalPrice());
		}
		else System.out.print("Model name" + modelName + "not found");
	}
	public void printModelWithOptionChoices(String modelName) {
		
	}
	public ArrayList<String> getModelNameList () {
		ArrayList<String> modelList = new ArrayList<String> ();
		modelList.addAll(_autoModelGroup.keySet());
		return modelList;
	}
	public void sendAuto (ObjectOutputStream objectOut, String modelName) { //gets an automobile before sending it over an ObjectOutputStream
		_autoModel = _autoModelGroup.get(modelName);
		if (_autoModel == null) { 
			System.err.println("Model not found! Model: " + modelName);
		}
		try {
			objectOut.writeObject(_autoModel);
			objectOut.flush();
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		
	}
	public ArrayList<String> getOptionSetNames (String modelName)
	{
		return _autoModelGroup.get(modelName).getAllOptSetNames();
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
				if(_autoModel.updateOptionName(setIndex, oldOptVal, newOptVal))
					System.out.println(newOptVal + " set!");
			}
		}
	}
	//a separate path with waits, just in case I forget to remove sleep() for the next lab.
	public void waitUpdateOptionValue(String modelName, String optionName, String oldOptVal, String newOptVal) {
		System.out.println ("Demonstrating Thread named " + Thread.currentThread().getName()
				+ " and Setting " + oldOptVal +  " to " + newOptVal);
		int setIndex;
		_autoModel = _autoModelGroup.get(modelName);
		if (_autoModel != null) {
			setIndex = _autoModel.findOptionSetIndex(optionName);
			if (setIndex != -1) {
				if(_autoModel.updateOptionName2(setIndex, oldOptVal, newOptVal)) {
					System.out.println(newOptVal + " set and updateOptionName2 exited.");
				}
					
				else { 
					System.out.println(newOptVal + " NOT set but updateOptionName2 exited."); 
				}
			} 
		}
		System.out.println (Thread.currentThread().getName() + " done.");
	}
	public void updateOptionChoice (String modelName, String optSetName, String optName) {
		_autoModel = _autoModelGroup.get(modelName);
		_autoModel.setOptionChoice(optSetName, optName);
	}
	public void removeAuto (String modelName) {	
		_autoModelGroup.remove(modelName);
	}
	public void addAuto(Model autoModel) {
		if (!_autoModelGroup.containsKey(autoModel.getModelName())) {
			_autoModelGroup.put(autoModel.getModelName(), autoModel);
		}
	}
	public boolean isAutoHere (String modelName) {
		return (_autoModelGroup.containsKey(modelName));
	}
	public boolean isAutoOnServer (String modelName) {
		return(isAutoHere(modelName));
	}
	public void loadLocalAutos () {
		System.err.println ("Please use addAuto when adding autos in this Class.");
	}
}
