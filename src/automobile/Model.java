/* Kimberly Disher
 * CIS 35B Lab 1
 */
package automobile;

import java.io.Serializable;
import java.text.*;
import java.util.*;

public class Model implements Serializable{
	private OptionSet [] _optset;
	private String _modelName;
	private double _price;		// the model's base price
	private int _optsetCount;	//we're using an array, some of which may be empty. This tracks the count of non-null items
	
	private static final long serialVersionUID = 1158L;  //so when we make changes, old versions become incompatible
	
	public Model() {
		_optsetCount = 0;
		initOptionSets(0);
	}
	public Model(String nm, double price) {
		this ();
		setModelPrice(price);
		setModelName(nm);
	}
	public Model(String nm, OptionSet [] opts, double price) {
		this(nm, price);
		setOptionSet(opts);
	}
	public Model(String nm, int optSetSize, double price) {
		this(nm, price);
		initOptionSets (optSetSize);
    }
	public void initOptionSets (int numSets) {this._optset = new OptionSet [numSets];} //set up empty array with initial count
	
	public String getModelName() {return _modelName;}
	
	public OptionSet[] getAllOptionSets() {return _optset;}
	
	public OptionSet getOptionSet(int index) {return this._optset[index];}
	
	public OptionSet getOptionSet (String setName) {
		int setIndex = findOptionSetIndex(setName);
		if (setIndex > -1) {
			return this._optset[setIndex];
		}
		else return null;
	}
	public double getModelPrice() {return _price;}
	
	//Yep, these return Double. We return null if there is a problem getting the number. Planning this for use with error handling later.
	public Double getOptionPrice(String optSetName, String optValue) {
		int setIndex = findOptionSetIndex (optSetName);
		return getOptionPrice (setIndex, optValue);	
	}
	public Double getOptionPrice(int optSetIndex, String optValue) {
		if ((optSetIndex > -1) && (optSetIndex < this._optsetCount))
			return this._optset[optSetIndex].getOptionPriceByValue(optValue);
		else return null;
	}
	public Double getOptionPrice(int optSetIndex, int optIndex) {
		boolean inbounds = false;
		if (optSetIndex > -1 && optSetIndex < this._optsetCount) {
			if (optIndex > -1 && optIndex > this._optset[optSetIndex].getOptionCount()) {
				inbounds = true;
			}
		}
		if (inbounds)
			return this._optset[optSetIndex].getOptionPrice(optIndex);
		else return null;
	}
    public String getOptionValue (int optSetIndex, int optIndex) {
    	boolean inbounds = false;
    	if (optSetIndex > -1 && optSetIndex < this._optsetCount) {
    		if (optIndex > -1 && optIndex < this._optset[optSetIndex].getOptionCount()){
    			inbounds = true;
    		}
    	}
    	if (inbounds)
    		return this._optset[optSetIndex].getOptionValue(optIndex);
    	else return null;
    }
	public void setModelName(String name) {this._modelName = name;}
	
	public void setOptionSet(OptionSet[] optset) {this._optset = optset;}
	
	public void setModelPrice(double price) {this._price = price;}
	
	public boolean addOptionSet(String setName, OptionSet.Option [] opts){
		if (addOptionSet(setName, opts.length)){
			_optset[_optsetCount -1].setOptions(opts);
			return true;
		}
		else return false;
	}
	public boolean addOptionSet(String setName, int setSize) {
		if (_optsetCount < _optset.length) {
			_optset[_optsetCount++] = new OptionSet(setSize, setName);
			return true;
		}
		else return false;
	}
	public boolean addOptionToSet(String setName, String optVal, double optPrice){
		int setIndex = findOptionSetIndex(setName);
		return this.addOptionToSet(setIndex, optVal, optPrice);
	}
	public boolean addOptionToSet(int setIndex, String optVal, double optPrice){
		if (setIndex > -1 && setIndex < this._optsetCount)
			return this._optset[setIndex].addOption(optVal, optPrice);
		else return false;
	}
	public boolean addOptionToLastSet (String optVal, double optPrice){
		return this.addOptionToSet(this._optsetCount - 1, optVal, optPrice);
	}
	public int findOptionSetIndex(String name){
		int index = 0;
        boolean found = false;
        while (!found && index < this._optsetCount)
        {
        	if (_optset[index].getOptName().indexOf(name) != -1)
        		found = true;
        	else index++;
        }
        if (found) 
        	return index;
        else return -1;
	}
	public boolean updateOptionSetSize (int setIndex, int size){
		boolean updated = false;
		if (setIndex > -1 && setIndex < this._optsetCount) {
			updated = this._optset[setIndex].updateOptionsSize(size);
		}
		return updated;
	}
	public boolean updateOptionSetName (int setIndex, String setName) {
		boolean updated = false;
		if (setIndex > -1 && setIndex < this._optsetCount) {
			this._optset[setIndex].setOptName(setName);
			updated = true;
		}
		return updated;
	}
	//updating option price and option value/name separately. I suspect the price will be the one updated more often,
	//and this leaves less chance of messing up both when we want to only change one.
	public boolean updateOptionPrice(int setIndex, String optName, double optPrice) {
		return this._optset[setIndex].updateOptionPrice(optName, optPrice);
	}
	public boolean updateOptionName(int setIndex, String optName, String newOptName) {
		return this._optset[setIndex].updateOptionValue(optName, newOptName);
	}
	
	public int findOptionIndex(int setIndex, String optValue)  {
		return this._optset[setIndex].findOptionIndexByValue(optValue);
	}
	public boolean deleteOptionSet (int setIndex) {
		boolean deleted = false;
		if (setIndex > -1 && setIndex < this._optsetCount) {
			this._optset[setIndex] = null;
			deleted = this.moveUpOptSets(setIndex);
			this._optsetCount--;
		}
		return deleted;
	}
	public boolean deleteOption(int setIndex, String optValue){
		int optIndex;
		boolean deleted = false;
		if (setIndex > -1 && setIndex < this._optsetCount) {
			optIndex = this._optset[setIndex].findOptionIndexByValue(optValue);
			if (optIndex != -1) {
				deleted = deleteOption(setIndex, optIndex);
			}
		}
		return deleted;
	}
	public boolean deleteOption(String setName, String optValue){
		boolean deleted = false;
		int setIndex = findOptionSetIndex(setName);
		if (setIndex > -1 && setIndex < this._optsetCount) {
			deleted = deleteOption(setIndex, optValue);
		}
		return deleted;
	}
	public boolean deleteOption(int setIndex, int optIndex) {return this._optset[setIndex].deleteOption(optIndex);}
	
	public String toString(){
		StringBuilder sb = new StringBuilder(this._modelName);
		sb.append(" Base Price: ");
		sb.append(NumberFormat
                        .getCurrencyInstance(new Locale("en", "US"))
                        .format(_price));
		sb.append("\n");
		for(int i = 0; i < _optsetCount; i++){
			sb.append("    ");		//using spaces, since tab sizes vary by system/
			sb.append(this._optset[i].toStringHelper());
		}
        return sb.toString();
	}
	private boolean moveUpOptSets (int emptyIndex){
		try{
			while (emptyIndex < this._optsetCount - 1){ //optsetCount is supposed to hold the next empty index at the end of the array
				this._optset[emptyIndex] = this._optset[emptyIndex + 1];
				emptyIndex++;
			}
			this._optset[emptyIndex] = null;
			return true;
		}
		catch (IndexOutOfBoundsException e) {
			return false;
		}
	}
}
