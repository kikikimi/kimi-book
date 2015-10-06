/*Kimberly Disher
 * CIS 35B Lab 1
 */
package tester;

import automobile.*;
import util.*;

public class Driver {

	public static void main(String[] args) {
		FileIO modelMaker = new FileIO();
		Model car = new Model();
		Model car2 = null;
		car = makeCarModel(modelMaker, car);
		printModel(car, "First Loaded:");
		addColorToModel (car);
		printModel(car, "Added a color option:");
		removeColorFromModel(car);
		printModel(car, "Removed a color option: ");
		modelMaker.serializeAutoObject("carLab1.ser", car);
		car2 = modelMaker.deserializeAutoModelObject("carLab1.ser", car2);
		printModel(car2, "Serialized and Deserialized:");
	}
	//Let's build a Model instance!
	private static Model makeCarModel (FileIO modelMaker, Model car){
		return modelMaker.buildAutoModelObject("automobile.txt", car);
	}
	//test an add method
	private static void addColorToModel (Model car){
		if (car != null){
			car.updateOptionSetSize(0, 11);
			car.addOptionToSet("Color", "Super Grape Metallic Clearcoat", 0D);
		}
	}
	
	//Let's test some delete methods
	private static void removeColorFromModel(Model car){
		if (car != null){
			car.deleteOption("Color", "Super Grape Metallic Clearcoat");
//			car.deleteOption("Color", "Pitch Black Clearcoat");
//			car.deleteOptionSet(0);
		}
	}
	private static void printModel (Model car, String changeMsg)
	{
		System.out.println(changeMsg);
		System.out.println(car.toString());
		System.out.println("_____________________");
	}
}
