/* Kimberly Disher 
 * CIS 35B Lab 2
 */
package tester;
import adapter.*;

public class Driver2 {

	public static void main(String[] args) {
		testApi();
		testErrorHandling();
	}
	
	private static void testApi (){
		//load values into model and print
		CanCreateModel car1 = new ModelBuilder();
		car1.buildAuto("automobile.txt");
		System.out.println("Loaded by buildAuto and printed by printAuto:");
		car1.printAuto("Ford Focus Wagon ZTW");
		System.out.println();
		
		//Change an Option and set name. and print
		CanUpdateModel car2 = new ModelBuilder ();
		car2.updateOptionSetName("Ford Focus Wagon ZTW", "Transmission", "Driver Transmission");
		car2.updateOptionValue("Ford Focus Wagon ZTW", "Color", "Cloud 9 White Clearcoat", "Blinding White Clearcoat");
		System.out.println ("----------");
		System.out.println("Transmission option name changed and Color.Cloud 9 White Clearcoat value changed");
		car1.printAuto("Ford Focus Wagon ZTW");
		System.out.println();
		car1.removeAuto("Ford Focus Wagon ZTW");
	}
	private static void testErrorHandling ()
	{
		CanCreateModel car1 = new ModelBuilder();
		car1.buildAuto("anautomobile.txt");			//file that doesn't exist. Error handling looks for a default file instead
		System.out.println("Done."); 
		car1.removeAuto("Ford Focus Wagon ZTW");
		car1.buildAuto("broken_automobile.txt");	//file that has missing line elements
		System.out.println("Loaded Model info for broken_automobile.txt");
		car1.printAuto("Ford Focus Wagon ZTW");
		
	}

}
