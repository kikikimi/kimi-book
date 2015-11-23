/* Kimberly Disher
 * CIS 35B
 * Lab 5
 * Parts of this class come from course materials, Networking.ppt, pg 43.
 */
package server;

import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ModelServer extends Thread implements client.SocketClientConstants, client.SocketClientInterface{ //not the best name, just didn't want to call yet another thing "Server"
	private ServerSocket svrSock;
	private Socket insock;
	private ObjectInputStream objectIn;
	AutoServer autoserver = new BuildCarModelOptions();
	
	private static int SVRPORT = 2092; 
	
	public ModelServer () {
		autoserver.loadLocalAutos();
		try{
			svrSock = new ServerSocket (SVRPORT);
			if (DEBUG) { System.out.println ("Server socket opened on " + svrSock.getLocalPort()); }
		}
		catch (IOException ie) { System.err.println ("Error opening a ServerSocket:" + ie.getMessage()); }
	}
	
	private void displayList (ArrayList <String> names) {
		for (int i = 0; i < names.size(); i++) {
			System.out.print (i + ". ");
			System.out.println (names.get(i));
		}
	}
	public boolean openConnection() {
		boolean opened = true;
		try { objectIn = new ObjectInputStream(insock.getInputStream());}
		catch (IOException ioe) { 
			System.out.println (ioe.getMessage());
			opened = false;
		}
		return opened;
	}
	public void handleSession() {
		Properties recProp;
		ObjectOutputStream objOut;
		boolean gettingInput = true;
		try {
			objOut = new ObjectOutputStream (insock.getOutputStream());
			objOut.flush();
			objOut.writeObject("WELCOME");
			if (DEBUG) { System.out.println ("WELCOME"); } 
			String cmd;
			while (gettingInput){
				cmd = (String) (objectIn.readObject());
				System.out.println ("Received command:" + cmd);
				if (cmd.equals("UPLOAD")) {
					recProp = (Properties)(objectIn.readObject());
					if(autoserver.buildAuto(recProp)) {
						objOut.writeObject("OK");
						if (DEBUG) { 
							System.out.println("UPLOAD OK");
							displayList (autoserver.getModelNameList());
						}
					}
					else objOut.writeObject("UPLOAD FAILED");
				}
				else if(cmd.equals("CONFIGURE")) {
					ArrayList <String> modelNames = autoserver.getModelNameList();
					objOut.writeObject(modelNames);
					objOut.flush();
					
					String model = (String)(objectIn.readObject());
					autoserver.sendAuto(objOut, model);
					objOut.writeObject("OK");
					if (DEBUG) { System.out.println("SENT AUTO");}
				}
				else if (cmd.equalsIgnoreCase("QUIT")) {
					objOut.flush();
					objOut.writeObject("Bye!");
					if (DEBUG) { System.out.println("CLIENT LEAVING");}
					gettingInput = false;	
				}
				else System.err.println("Received unexpected command from client.");
			}
		}
		catch (Exception e) {
			System.err.println (e.getMessage());
		}
	}
	public void closeSession () {
		try {
			objectIn.close();
		}
		catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	public void run () {
		try {
			while (true) {
				insock = svrSock.accept();
				if (DEBUG) { System.out.println ("Accepted a connection from " + insock.getInetAddress()); }
				if (openConnection ()) {
					handleSession ();
				}
				try{ sleep(1000); }
				catch (InterruptedException ie) { } 
			}
		}
		catch (IOException ioe) { System.err.println ("Error acceptiong a connection" + ioe.getMessage()); }
		catch (NullPointerException ne) { System.err.println("Make sure this port is not already in use."); }
	}
	public static void main (String args[]) {
		ModelServer mdSrv = new ModelServer();
		mdSrv.start();
	}
	
}
