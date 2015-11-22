/* Kimberly Disher
 * CIS 35B 
 * Added a new version of buildAuto for lab 5
 */
package adapter;
import java.util.*;

public interface CanCreateModel {
	public void buildAuto(String fileName, String fileType);
	public boolean buildAuto(Properties autoProp);
	public void printAuto(String modelName);
	public void removeAuto(String modelName);
}

