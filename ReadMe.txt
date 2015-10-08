This is Kimberly Disher's Lab2. In the top level folder, Lab2 are included output files "test_output.txt", 
from the console, "carLab1.ser", a serialized object file. "Automobile.txt" is the input
file to build a car model. The design diagram is Main.jpg.
Java source files Model.java and OptionSet.java are in src/automobile. FileIO.java is src/util. src/exception 
contains ErrorFix.java and AutoException.java. AutoException is our custom exception with a fixError method. 
ErrorFix contains supporting error fix methods.

New classes and interfaces are also in src/adapter. ProxyAuto is where most of the action methods are. ModelBuilder 
is my alternative name for BuildAuto. CanUpdateModel, CanFixModel, and CanCreateModel are alternative names 
for UpdateAuto, FixAuto, and CreateAuto, because I like the idea of adjective-like interface names.

The driver with main is located at src/tester/driver2.java. 

