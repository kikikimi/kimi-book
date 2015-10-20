/* Kimberly Disher
 * CIS 35B
 * Updated for Lab 3
 */
package automobile;

import java.io.Serializable;
import java.text.*;
import java.util.*;

public class Model implements Serializable{
	private ArrayList<OptionSet> _optset;
	private String _modelName;
	private double _price;		// the model's base price
	
	private static final long serialVersionUID = 1158L;  //so when we make changes, old versions become incompatible
	
	public Model() {
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
	public void initOptionSets (int numSets) {this._optset = new ArrayList<OptionSet> (numSets);} //set up empty array with initial count
	
	public String getModelName() {return _modelName;}
	
	public ArrayList <OptionSet> getAllOptionSets() {return _optset;}
	
	public OptionSet getOptionSet(int index) {return this._optset.get(index);}
	
	public int getOptionSetSize () {return this._optset.size();}
	
	public OptionSet getOptionSet (String setName) {
		int setIndex = findOptionSetIndex(setName);
		if (setIndex > -1) {
			return this._optset.get(setIndex);
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
		if ((optSetIndex > -1) && (optSetIndex < this._optset.size()))
			return this._optset.get(optSetIndex).getOptionPriceByValue(optValue);
		else return null;
	}
	public int getOptionCount(int optSetIndex) {return this._optset.get(optSetIndex).getOptionCount();}
	
	public Double getOptionPrice(int optSetIndex, int optIndex) {
		boolean inbounds = false;
		if (optSetIndex > -1 && optSetIndex < this._optset.size()) {
			if (optIndex > -1 && optIndex < this._optset.get(optSetIndex).getOptionCount()) {
				inbounds = true;
			}
		}
		if (inbounds)
			return this._optset.get(optSetIndex).getOptionPrice(optIndex);
		else return null;
	}
	public String getOptionSetName(int optSetIndex) {return this._optset.get(optSetIndex).getOptName();}
	
    public String getOptionValue (int optSetIndex, int optIndex) {
    	boolean inbounds = false;
    	if (optSetIndex > -1 && optSetIndex < this._optset.size()) {
    		if (optIndex > -1 && optIndex < this._optset.get(optSetIndex).getOptionCount()){
    			inbounds = true;
    		}
    	}
    	if (inbounds)
    		return this._optset.get(optSetIndex).getOptionValue(optIndex);
    	else return null;
    }
    
    public String getOptionChoice (String setName) {
    	int index = findOptionSetIndex(setName);
    	if (index > -1) {
    		return _optset.get(index).getOptionChoice().getOptValue();
    	}
    	else return null;
    }
    public Double getOptionChoicePrice(String setName) {
    	int setIndex = findOptionSetIndex(setName);
    	if (setIndex != -1 && _optset.get(setIndex).getOptionChoice() != null) {
    			return _optset.get(setIndex).getOptionChoice().getOptPrice();
    	}
    	else return null;
    }	
    public double getTotalPrice () {
		double price = 0.0;
		ListIterator<OptionSet> optIterator = _optset.listIterator();
		price += this._price;
		while (optIterator.hasNext()) {
			price += optIterator.next().getOptionChoice().getOptPrice();
		}
		return price;
	}
	public void setModelName(String name) {this._modelName = name;}
	
	public void setOptionSet(OptionSet[] optset) {
		for (OptionSet subOptSet : optset) {
			this._optset.add(subOptSet);
		}
	}
	public void setModelPrice(double price) {this._price = price;}
	
	public boolean setOptionChoice (String setName, String optName) {
		boolean choiceSet = false;
		int setIndex = this.findOptionSetIndex(setName);
		int optIndex;
		OptionSet tempOptSet;
		if (setIndex > -1) {
			tempOptSet = this._optset.get(setIndex);
			optIndex = tempOptSet.findOptionIndexByValue(optName);
			if (optIndex > -1) {
				tempOptSet.setOptionChoice(optName);
				choiceSet = true;
			}
		}
		return choiceSet;
	}
	public void setDefaultOptionChoices ()
	{
		int i = 0;
		while (i < _optset.size()) {
			_optset.get(i++).setDefaultOptionChoice();
		}
	}
	public boolean addOptionSet(String setName, OptionSet.Option [] opts){
		if (addOptionSet(setName, opts.length)){
			this._optset.get(_optset.size() - 1).setOptions(opts);
			return true;
		}
		else return false;
	}
	public boolean addOptionSet(String setName, int setSize) {return _optset.add(new OptionSet(setSize, setName));}
	
	public boolean addOptionToSet(String setName, String optVal, double optPrice){
		int setIndex = findOptionSetIndex(setName);
		return this.addOptionToSet(setIndex, optVal, optPrice);
	}
	public boolean addOptionToSet(int setIndex, String optVal, double optPrice){
		if (setIndex > -1 && setIndex < this._optset.size())
			return this._optset.get(setIndex).addOption(optVal, optPrice);
		else return false;
	}
	public boolean addOptionToLastSet (String optVal, double optPrice){
		return this.addOptionToSet(this._optset.size() - 1, optVal, optPrice);
	}
	public int findOptionSetIndex(String name){
		int index = 0;
        boolean found = false;
        while (!found && index < this._optset.size())
        {
        	if (this._optset.get(index).getOptName().indexOf(name) != -1)
        		found = true;
        	else index++;
        }
        if (found) 
        	return index;
        else return -1;
	}
	public boolean updateOptionSetName (int setIndex, String setName) {
		boolean updated = false;
		if (setIndex > -1 && setIndex < this._optset.size()) {
			this._optset.get(setIndex).setOptName(setName);
			updated = true;
		}
		return updated;
	}
	//updating option price and option value/name separately. I suspect the price will be the one updated more often,
	//and this leaves less chance of messing up both when we want to only change one.
	public boolean updateOptionPrice(int setIndex, String optName, double optPrice) {
		return this._optset.get(setIndex).updateOptionPrice(optName, optPrice);
	}
	public boolean updateOptionName(int setIndex, String optName, String newOptName) {
		return this._optset.get(setIndex).updateOptionValue(optName, newOptName);
	}
	
	public int findOptionIndex(int setIndex, String optValue)  {
		return this._optset.get(setIndex).findOptionIndexByValue(optValue);
	}
	public boolean deleteOptionSet (int setIndex) {
		boolean deleted = false;
		if (setIndex > -1 && setIndex < this._optset.size()) {
			this._optset.remove(setIndex);
		}
		return deleted;
	}
	public boolean deleteOption(int setIndex, String optValue){
		int optIndex;
		boolean deleted = false;
		if (setIndex > -1 && setIndex < this._optset.size()) {
			optIndex = this._optset.get(setIndex).findOptionIndexByValue(optValue);
			if (optIndex != -1) {
				deleted = deleteOption(setIndex, optIndex);
			}
		}
		return deleted;
	}
	public boolean deleteOption(String setName, String optValue){
		int setIndex = findOptionSetIndex(setName);
		return this.deleteOption(setIndex, optValue);
	}
	public boolean deleteOption(int setIndex, int optIndex) {return this._optset.get(setIndex).deleteOption(optIndex);}

	public String toString(){
		ListIterator<OptionSet> osIterator = _optset.listIterator();
		StringBuilder sb = new StringBuilder(this._modelName);
		sb.append(" Base Price: ");
		sb.append(NumberFormat
                        .getCurrencyInstance(new Locale("en", "US"))
                        .format(_price));
		sb.append("\n");
		while (osIterator.hasNext()) {
			sb.append("    ");		//using spaces, since tab sizes vary by system/
			sb.append(osIterator.next().toStringHelper());
		}
        return sb.toString();
	}
}
