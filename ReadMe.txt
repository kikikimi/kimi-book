This is Kimberly Disher's Lab3. 

Output files - 
test_output.txt (console/system.err output)
fixedbroken_filename.txt (Model config file with saved error fixes)
 
Input files -

automobile.txt (config for a auto Model). 
automobile2.txt (different config for an auto Model).
broken_autombile.txt (Model config file with errors for Exception testing)

Updated Classes for Lab 3
ProxyAuto -- Updated to use a LinkedHashMap<Model> for a group of autos
			 added addAuto, and updated removeAuto to handle Model instances in a LinkedHashMap.

Model -- now uses an ArrayList<OptionSet> for option sets instead of an array. Counters and 
		 	methods dealing with the old array have been deleted (optionSetCount).
		 added getters/setters for Option choice name and prices in OptionSet.
		 Added getTotalPrice to add option choice prices to model price.
		 Added toStringWChoices to print Model and show selected options.
		 
OptionSet -- now uses an ArrayList<Option> for options instead of an array. Counters and methods 
				used in array housekeeping are gone (moveUpOptions).
			added Option _optChoice and associated get/set methods.
			toStringHelper modified to have an option of showing selected options.

The driver with main is located at src/tester/driver3.java. 
The design diagram is Lab3Diagram.jpg

Here are errors and codes added in Lab 2:

10404 file not found
10206 missing element in option line 
102061 missing element in option set line
102062 number not in correct format --used for both OptionSet and Option lines
102063 option set count not in correct format
102064 model name missing


