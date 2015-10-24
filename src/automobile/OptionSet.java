/* Kimberly Disher
 * CIS 35B
 * Updated for Lab 3
 */
package automobile;

import java.text.*;
import java.util.*;
import java.io.Serializable;

class OptionSet implements Serializable{
	private String _optName;
	private ArrayList <Option> _options;
	private Option _optChoice;
	private static final long serialVersionUID = 1158L;  //so when we make changes, old versions become incompatible
	
	OptionSet() {}
	OptionSet(String nm) {
		setOptName (nm);
	}
	OptionSet(int size, String nm) {
		this (nm);
		this._options = new ArrayList<Option>(size);
	}
	protected String getOptName() {return _optName;} //Option Set Name
	
	protected Option getOption (int index) {return this._options.get(index);}
	
	protected Option getOption(String value) {return this._options.get(this.findOptionIndexByValue(value));}
	
	protected ArrayList<Option> getOptions() {return _options;}
	
	protected int getOptionCount() {return this._options.size();}    
	
	protected Option getOptionChoice() {return this._optChoice;}
        
	protected double getOptionPrice (int index) {return this._options.get(index).getOptPrice();}
	
	protected String getOptionValue (int index) {return this._options.get(index).getOptValue();}
	
	protected int getOpsetSize() {return this.getOptionCount();}
	
	protected Double getOptionPriceByValue (String optValue) {
		int optIndex = findOptionIndexByValue(optValue);
		if (optIndex > -1)
			return this._options.get(optIndex).getOptPrice();
		else return null;
	}
	protected void setDefaultOptionChoice() {
		_optChoice = _options.get(0);
	}
	protected void setOptName(String optName) {this._optName = optName;}
	
	protected void setOptions(Option[] options) {	//copy the contents, we don't trust that the source array won't be messed with.
		for (Option opt : options){
			this.addOption (opt._optValue, opt._optPrice);
		}
	}
	protected void setOptionChoice(String optValue) {
		_optChoice = _options.get(this.findOptionIndexByValue(optValue));
	}
	protected boolean addOption (String optValue, double optPrice){
		return _options.add(new Option(optValue, optPrice));
	}
	protected boolean updateOptionPrice (String optVal, double price) {
		boolean updated = false;
		int optIndex = this.findOptionIndexByValue(optVal);
		if (optIndex != -1) {
			this._options.get(optIndex).setOptPrice(price);
			updated = true;
		}
		return updated;
	}
	protected boolean updateOptionValue (String optVal, String newOptVal) {
		boolean updated = false;
		int optIndex = this.findOptionIndexByValue(optVal);
		if (optIndex != -1) {
			this._options.get(optIndex).setOptValue(newOptVal);
			updated = true;
		}
		return updated;
	}
	protected boolean deleteOption (String optValue) {
		return deleteOption(findOptionIndexByValue(optValue));
	}
    protected boolean deleteOption (int optIndex) {
    	boolean deleted = false;
    	if (optIndex > -1 && optIndex < this._options.size()) {
    		_options.remove(optIndex);
    		deleted = true;
    	}
    	return deleted;
    }
    protected int findOptionIndexByValue (String optValue) {
    	int index = 0;
        boolean found = false;
        
        while (!found && index < _options.size()){
        	if (_options.get(index).getOptValue().indexOf(optValue) != -1) {
        		found = true;
        	}
        	else index++;
        }
        if (found) 
        	return index;
        else return -1;
    }
	protected String toStringHelper(boolean printChoice) {
		StringBuilder sb = new StringBuilder (_optName);
		sb.append("\n");
		for (int i = 0; i < this._options.size(); i++) {
			if (printChoice && this._options.get(i) == this._optChoice)
				sb.append("    X   ");
			else 
				sb.append("        "); 			// using spaces, since tab size varies by system
			sb.append(this._options.get(i).toStringHelper());
			sb.append("\n");
		}
        return sb.toString();
	}   
	protected class Option implements Serializable {
		private String _optValue;
		private double _optPrice;
		private static final long serialVersionUID = 1158L;
		
		protected Option (){}
		
		protected Option (Option opt) { 
			this._optValue = opt._optValue;
			this._optPrice = opt._optPrice;
		}
		protected Option (String value, double price) {
			setOptValue(value);
			setOptPrice(price);
		}
		protected String getOptValue() {return _optValue;}
		
		protected double getOptPrice() {return _optPrice;}
		
		protected void setOptValue(String optValue) {this._optValue = optValue;}
		
		protected void setOptPrice(double optPrice) {this._optPrice = optPrice;}
		
		protected void deleteOption () {
			this._optPrice = 0;
			this._optValue = "";
		}
		protected String toStringHelper() {
			StringBuilder sb = new StringBuilder (_optValue);
			sb.append(", Price: ");
			sb.append(NumberFormat
                                .getCurrencyInstance(new Locale("en", "US"))
                                .format(_optPrice));
			return sb.toString();
		}
	}
}
