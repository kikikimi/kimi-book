/* Kimberly Disher
 * CIS 35B
 * New for Lab 5
 */
package server;

import java.util.ArrayList;
import java.util.Properties;
import java.io.*;
import java.util.*;

import adapter.ModelBuilder;

public class BuildCarModelOptions implements AutoServer {
	private static ModelBuilder _carBuilder = null;
	
	public boolean buildAuto(Properties autoProp) {
		if (_carBuilder == null) {	//make sure we have an instance, and not just creating another one.
			_carBuilder = ModelBuilder.getInstance();
		}
		return _carBuilder.buildAuto(autoProp);
	}
	public ArrayList<String> getModelNameList () {
		if (_carBuilder == null) { //make sure we have an instance, and don't just waste memory creating new ones
			_carBuilder = ModelBuilder.getInstance();
		}
		return _carBuilder.getModelNameList();
	}
	public boolean isAutoOnServer (String modelName) {
		if (_carBuilder == null) { //make sure we have an instance, and don't just waste memory creating new ones
			_carBuilder = ModelBuilder.getInstance();
		}
		return _carBuilder.isAutoHere(modelName);
	}
	public void loadLocalAutos () {
		if (_carBuilder == null) { //make sure we have an instance, and don't just waste memory creating new ones
			_carBuilder = ModelBuilder.getInstance();
		}
		_carBuilder.buildAuto("automobile.txt", "Text");	//this buildAuto calls addAuto, which only adds a Model if 
		_carBuilder.buildAuto("automobile2.txt", "Text");	// the Model name does not already exist in the LinkedHashMap
	}
	public void sendAuto(ObjectOutputStream objectOut, String modelName) {
		if (_carBuilder == null) { //make sure we have an instance, and don't just waste memory creating new ones
			_carBuilder = ModelBuilder.getInstance();
		}
		_carBuilder.sendAuto(objectOut, modelName);
	}
}
