This is Kimberly Disher's Lab2. In the top level folder, Lab2 are included output files "test_output.txt", 
from the console, "carLab1.ser", a serialized object file. "Automobile.txt" is the input
file to build a car model. broken_autombile.txt contains errors to test the error handling in AutoException.java
 The design diagram is Main.jpg.
Java source files Model.java and OptionSet.java are in src/automobile. FileIO.java is src/util. src/exception 
contains ErrorFix.java and AutoException.java. AutoException is our custom exception with a fixError method. 
ErrorFix contains supporting error fix methods.

Here are errors and codes used in this version:

10404 file not found
10206 missing element in option line 
102061 missing element in option set line
102062 number not in correct format --used for both Optionset and Option lines
102063 option set count not in correct format
102064 model name missing

New classes and interfaces are also in src/adapter. ProxyAuto is where most of the action methods are. ModelBuilder 
is my alternative name for BuildAuto. CanUpdateModel, CanFixModel, and CanCreateModel are alternative names 
for UpdateAuto, FixAuto, and CreateAuto, because I like the idea of adjective-like interface names.

The driver with main is located at src/tester/driver2.java. 

Note:
Some of the error-fixes may not be considered true self-healing. When a value is missing, the app goes 
to the console to ask for a new value.  I've learned on the job that sometimes a developer defaulting to a 
value can go missed by QA and cost thousands in production. My approach is bringing these errors to light, 
especially missing prices. 


