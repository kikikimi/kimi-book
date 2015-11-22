/*Kimberly Disher
 * CIS 35B
 * Lab 5
 * Code from class materials Networking.ppt pg 42 
 */
package client;

public interface SocketClientInterface {
	 boolean openConnection();
     void handleSession();
     void closeSession();
}
