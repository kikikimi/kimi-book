/*	Kimberly Disher	
 *  CIS 35B
 *  Driver for Lab4
 */
package tester;

import adapter.*;
import scale.*;

public class Driver4 {
	public static void main(String[] args) {
		CanCreateModel car1 = new ModelBuilder();
		car1.buildAuto("automobile.txt", "text");
		car1.buildAuto("automobile2.txt", "text");
		testThreads ();
		System.out.println();
		car1.printAuto("Ford Focus Sedan");
		testThreadsForLocking();
		System.out.println();
		car1.printAuto("Ford Focus Wagon ZTW");
	}
	private static void testThreads() {
		EditOptions edOpt1 = new EditOptions (new String []{"Ford Focus Sedan", "Transmission", "Standard", "6 Speed Manual", "false"});
		EditOptions edOpt2 = new EditOptions (new String []{"Ford Focus Sedan", "Transmission", "Standard", "5 Speed Manual", "false"});
		
		Thread thread1 = new Thread(edOpt1);
		Thread thread2 = new Thread(edOpt2);
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		}
		catch (InterruptedException ie) {
			System.err.print ("Error:" + ie.getMessage());
		}	
	}
	private static void testThreadsForLocking() {
		EditOptions edOpt1 = new EditOptions (new String []{"Ford Focus Wagon ZTW", "Transmission", "Standard", "6 Speed Manual", "true"});
		EditOptions edOpt2 = new EditOptions (new String []{"Ford Focus Wagon ZTW", "Transmission", "Standard", "5 Speed Manual", "true"});
		
		Thread thread1 = new Thread(edOpt1);
		Thread thread2 = new Thread(edOpt2);
		thread2.start();
		thread1.start();
		try {
			thread2.join();
			thread1.join();
		}
		catch (InterruptedException ie) {
			System.err.print ("Error:" + ie.getMessage());
		}	
	}
}
