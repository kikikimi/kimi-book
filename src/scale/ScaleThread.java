/* Kimberly Disher
 * CIS 35B
 * New for Lab 4
 */
package scale;

public interface ScaleThread extends Runnable{
	public void updateOptionPrice(String modelName, String optionName, String optVal, float newprice);
	public void updateOptionSetName(String modelName, String optionSetName, String newName);
	public void updateOptionValue(String modelName, String optionName, String oldOptVal, String newOptVal);
}
