This is Kimberly Disher's Lab5. 

Output files - 
test_output.txt (server and client console output)
 
Input files -
Used when starting a Server:
automobile.txt (config for a auto Model). 
automobile2.txt (different config for an auto Model).

Used for uploading from a Client:
automobile.properties
auto2.properties

Added for Lab 5
Lab 5 adds client/server capabilities in new packages "client" and "server." 
The CarModelOptionsIO class in client can upload a properties file, 
and receive an Model object to configure and then display.
The ModelServer class in server can receive and interpret some commands,  
send a Model object over a socket connection and 
receive a properties object over a connection.

Updates for Lab 5

The FileIO class includes a new method, parseProperties(). 
ProxyAuto and the CanCreateModel interface call it via a new buildAuto();
Model's toStringWChoices() now supports printing only the option choice per each option set.

The "drivers" with main is located at src/client/CarModelOptionsIO.java and
	src/server/ModelServer.java. 
The design diagram is Lab5Diagram.jpg

---------

Main changes from Lab 4 -demonstrates capabilities for threading and scaling, data changing methods in Model are synchronized.
Main changes from Lab 3 - added a LinkedHashMap to handle a group of Model Object in ProxyAuto. Changed _optset in Model and 
		_options in OptionSet to ArrayLists from simple arrays.

Here are errors and codes added in Lab 2:

10404 file not found
10206 missing element in option line 
102061 missing element in option set line
102062 number not in correct format --used for both OptionSet and Option lines
102063 option set count not in correct format
102064 model name missing


