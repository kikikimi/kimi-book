package adapter;

import java.util.ArrayList;

public interface CanSelectOptions {
	public void updateOptionChoice (String modelName, String optSetName, String optName);
	public void printModelWithOptionChoices(String modelName);
	public ArrayList<String> getOptionSetNames (String modelName);
	
}
