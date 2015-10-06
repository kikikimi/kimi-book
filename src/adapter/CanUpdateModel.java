/* Kimberly Disher
 * CIS 35B Lab 2
 * This interface will contain method declarations for updating all Model, OptionSet, and Option
 * data, eventually.
 */
package adapter;

public interface CanUpdateModel {
	public void updateOptionPrice(String modelName, String optionName, String optVal, float newprice);
	public void updateOptionSetName(String modelName, String optionSetName, String newName);
}
