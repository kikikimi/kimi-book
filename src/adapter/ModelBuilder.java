/*Kimberly Disher
 * CIS 35B
 * Updated for Lab 5
 */
package adapter;
import server.*;

public class ModelBuilder extends ProxyAuto 
implements CanCreateModel, CanUpdateModel, CanFixModel, CanSelectOptions, AutoServer {
	private static ModelBuilder _carBuilder = new ModelBuilder();
	
	public static ModelBuilder getInstance () { return _carBuilder;} 
}
