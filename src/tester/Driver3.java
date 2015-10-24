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
				System.out.println("First model");
				car1.printAuto("Ford Focus Wagon ZTW");
				System.out.println();
				car1.buildAuto("automobile2.txt");
				System.out.println("Second model");
				car1.printAuto("Ford Focus Sedan");
				System.out.println();
				car1.buildAuto("automobile3.txt");
				System.out.println("Second model");
				car1.printAuto("Ford Avenger Wagon");
				System.out.println();
	}

}
