This is Kimberly Disher's Lab4. 

Output files - 
test_output.txt (console/system.err output)
 
Input files -

automobile.txt (config for a auto Model). 
automobile2.txt (different config for an auto Model).

Added for Lab 4
Lab 4 adds capabilities for threading and scaling. Method named updateOptionName2 includes a short wait 
to make object locking more noticeable.
Interface ScaleThread in package scale -- extends Runnable
Class EditOptions in package scale -- extends ProxyAuto, implements ScaleThread to use threading

Updated Classes for Lab 4
ProxyAuto -- added method waitUpdateOptionValue to call updateOptionName2 so that the synchronized methods can be demonstrated
			on a human time scale without altering the non-demonstration path of operations in the class.

Model -- added synchronized keyword to public methods that change data members in Model, Option and OptionSet.
		 added method updateOptionName2 to add a sleep() and not change the operations of the original updateOptionName.

The driver with main is located at src/tester/driver4.java. 
The design diagram is Lab4Diagram.jpg

---------

Main Changes from Lab 3 - added a LinkedHashMap to handle a group of Model Object in ProxyAuto. Changed _optset in Model and 
		_options in OptionSet to ArrayLists from simple arrays.

Here are errors and codes added in Lab 2:

10404 file not found
10206 missing element in option line 
102061 missing element in option set line
102062 number not in correct format --used for both OptionSet and Option lines
102063 option set count not in correct format
102064 model name missing


