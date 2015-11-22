/*Kimberly Disher
 * CIS 35B
 * New for Lab 5
 */
package server;

import java.util.*;
import java.io.*;

public interface AutoServer {
	public boolean buildAuto (Properties autoProp);
	public ArrayList<String> getModelNameList();
	public boolean isAutoOnServer (String modelName);
	public void loadLocalAutos ();
	public void sendAuto (ObjectOutputStream objectOut, String modelName);
}
