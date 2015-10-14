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
		CanCreateModel car1 = new ModelBuilder();
		car1.buildAuto("automobile.txt");
		System.out.println("Loaded by buildAuto and printed by printAuto:");
		car1.printAuto("Ford Focus Wagon ZTW");
		CanUpdateModel car2 = new ModelBuilder ();
		car2.updateOptionSetName("Ford Focus Wagon ZTW", "Transmission", "Driver Transmission");
		car2.updateOptionValue("Ford Focus Wagon ZTW", "Color", "Cloud 9 White Clearcoat", "Blinding White Clearcoat");
		System.out.println ("----------");
		System.out.println("Transmission option name changed and Color.Cloud 9 White Cloearcoat value changed");
		car1.printAuto("Ford Focus Wagon ZTW");
		car1.deleteAuto("Ford Focus Wagon ZTW");
	}
	private static void testErrorHandling ()
	{
		CanCreateModel car1 = new ModelBuilder();
		car1.buildAuto("anautomobile.txt");
		car1.deleteAuto("Ford Focus Wagon ZTW");
		car1.buildAuto("broken_automobile.txt");
		car1.printAuto("Ford Focus Wagon ZTW");
		
	}

}
