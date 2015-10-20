/* Kimberly Disher
 * CIS 35B Lab 3
 */

package tester;

import adapter.*;

public class Driver3 {

	public static void main(String[] args) {
		//load values into model and print
				CanCreateModel car1 = new ModelBuilder();
				car1.buildAuto("automobile.txt");
				System.out.println("Loaded by buildAuto and printed by printAuto:");
				car1.printAuto("Ford Focus Wagon ZTW");
				System.out.println();

	}

}
