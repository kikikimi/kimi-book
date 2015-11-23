/* Kimberly Disher
 * CIS 35B
 * New for Lab 5
 * Parts of this class come from course materials, Networking.ppt, pg 43.
 */
package client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import automobile.Model;

public class CarModelOptionsIO extends Thread implements SocketClientInterface, SocketClientConstants{
	
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private String strHost;
	private int portNum;
	private Socket sock;
	private SelectCarModelOption carOptions;

	public CarModelOptionsIO (String shost, int portNum) {
		setHost(shost);
		setPort(portNum);
	}
	public void sendAutoProperties(String fileName, ObjectOutputStream objectOut) {
		Properties autoProps = new Properties();
		FileInputStream autoIn = null;
		try {
			autoIn = new FileInputStream(fileName);
		}
		catch(FileNotFoundException e) {
			System.err.println("Properties file not found!");
		}
		try{
			autoProps.load(autoIn);
		}
		catch(IOException e){
			System.err.println("Error reading Properties input stream!");
		}
		try {
			objectOut.writeObject(autoProps);
			objectOut.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Model buildAuto (ObjectInputStream objectIn) {
		Model car = null;
		try {
			car  = (Model) (objectIn.readObject());
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return car;
	}
	@SuppressWarnings("unchecked")
	public String recModelList (ObjectInputStream objectIn) {
		ArrayList<String> modelList = null;
		Object listObj;
		if (carOptions.equals(null))
			carOptions = new SelectCarModelOption();
		
		try {
			listObj = objectIn.readObject();
			if (listObj instanceof java.util.ArrayList<?>)
				modelList = (ArrayList<String>)listObj;
		}
		catch (IOException e){
			System.err.println("Problem receiving model list.");
		}
		catch (ClassNotFoundException ce) {}
		
		System.out.println("Select a model to configure:");
		carOptions.displayList(modelList);
		//adding 1 to size, so the model range is right for a 1-based start for display, 
		//subtracting it for a 0-based start for the get().
		return modelList.get(carOptions.promptInput ("vehicle model", 1, modelList.size()) - 1);
	} 

	public boolean openConnection(){
		try {
			sock = new Socket(strHost, portNum);
			if(DEBUG && sock.isConnected()) System.err.println("Connected!"); 
		}
		catch(IOException socketError){
			if (DEBUG) System.err.println("Unable to connect to " + strHost);
			return false;
		}
		try {
			writer = new ObjectOutputStream(sock.getOutputStream());
			reader = new ObjectInputStream(sock.getInputStream());

		}
		catch (Exception e){
			if (DEBUG) System.err.println
			("Unable to obtain stream to/from " + strHost);
			return false;
		} 
		return true;
	}
	public void closeSession(){
		try {
			writer = null;
			reader = null;
			sock.close();
		}
		catch (IOException e){
			if (DEBUG) System.err.println("Error closing socket to " + strHost);
		} 
	} 
	public void handleSession(){
		Object objInput = null;
		String localCmd;
		if (carOptions == null)
			carOptions = new SelectCarModelOption ();
		if (DEBUG) {
			System.out.println ("Handling session with " + strHost + ":" + portNum);
			quickAutoUpload();
			quickAutoDownload();
		}
		else {
			try {
				while ((objInput = reader.readObject())!= null){
					if (objInput.equals("OK") || objInput.equals("WELCOME")
							||objInput.equals("UPLOAD FAILED")) {
						if (objInput.equals("UPLOAD FAILED")){
							System.out.print ("UPLOAD FAILED\n");
						}
						carOptions.displayMenu();
						localCmd = carOptions.getCmd();
						writer.writeObject(localCmd);
						if (localCmd.equals("UPLOAD")){
							String fileName = carOptions.getPropertiesFileName();
							sendAutoProperties(fileName, writer);
						}
						if (localCmd.equals("CONFIGURE")) {
							String strModel = recModelList(reader);
							writer.writeObject(strModel);
							carOptions.configureAuto(buildAuto(reader));
						}
						if (localCmd.equals("QUIT")) {
							return;
						}	
					}
				}
			}	
			catch (IOException e){
				if (DEBUG) System.out.println ("Handling session with " + strHost + ":" + portNum);
			}
			catch (ClassNotFoundException ce) {}
		}
	} 
	@SuppressWarnings("unchecked")
	private void quickAutoDownload(){
		Object objInput= null; 
		String modName = "";
		Model testModel = null;
		try{
			objInput = reader.readObject();
		}
		catch (ClassNotFoundException cne) {}
		catch (IOException ie){}
		
		if (objInput.equals("OK") || objInput.equals("WELCOME"))  {
			try {
				writer.writeObject("CONFIGURE");
			}
			catch (IOException e) {System.out.println("Problem sending command CONFIGURE");}
			ArrayList<String> modelList = null;
			if (carOptions.equals(null))
				carOptions = new SelectCarModelOption ();
			
			try {
				objInput = reader.readObject();
				if (objInput instanceof java.util.ArrayList<?>)
					modelList = (ArrayList<String>)objInput;
				if (!modelList.isEmpty()) {
					modName = modelList.get(0);
					carOptions.displayList(modelList);
				}
			}
			catch (IOException e) {System.err.println("Problem receiving model list.");}
			catch (ClassNotFoundException ce) {}
			try{
				writer.writeObject(modName);
				testModel = buildAuto(reader);
			}
			catch (IOException e) {System.err.println("Problem getting Model data.");}
			
			carOptions.displayConfiguredAuto(testModel.toStringWChoices(false));
			try {
				writer.writeObject("QUIT");
				System.out.println((String)(reader.readObject()));
			}
			catch (IOException e) {System.out.println("Problem sending command QUIT");}
			catch (ClassNotFoundException cne) { System.out.println("problem getting ack after QUIT");}
		}
	}
	private void quickAutoUpload() {
		Object objInput= null; 
		
		try{
			objInput = reader.readObject();
		}
		catch (ClassNotFoundException cne) {}
		catch (IOException ie){}
		
		if (objInput.equals("OK") || objInput.equals("WELCOME"))  {
			try {
				writer.writeObject("UPLOAD");
				sendAutoProperties("automobile.properties", writer);
			}
			catch (IOException e) {System.out.println("Problem sending command UPLOAD");}	
		}
	} 
	//copied from DefaultSocketClient
	public void setHost(String strHost){
		this.strHost = strHost;
	} 
	//copied from DefaultSocketClient
	public void setPort(int iPort){
		this.portNum = iPort;
	} 
	//Copied from DefaultSocketClient
	public void run(){
		if (openConnection()) {
			handleSession();
	        closeSession();
	    }
	}
	public static void main (String args[]) {
		CarModelOptionsIO carClient = null;
		String host;
		int hostport;
		
		if (args.length == 2) { //let's accept command line arguments for host and port
			host = args[0];
			hostport = Integer.parseInt(args[1]);
		}
		else {
			host = "127.0.0.1";
			hostport = 2092;
		}
		System.out.println("Trying to connect to Auto Model Server at "  + host);
		carClient = new CarModelOptionsIO (host, hostport);
		if (!carClient.equals(null))
			carClient.start();	//vroom
		else System.err.println("Failed to create a client socket.");		
	}
}
