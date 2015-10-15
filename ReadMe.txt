This is Kimberly Disher's Lab2. 

Output files - 
test_output.txt (console/system.err output)
fixedbroken_filename.txt (Model config file with saved error fixes)
 
Input files -

Automobile.txt (config for a auto Model). 
broken_autombile.txt (Model config file with errors for Exception testing)

New classes and interfaces for Lab 2
adapter.ProxyAuto-- abstract class with methods for accessing Model.
adapter.ModelBuilder -- extends ProxyAuto
adapter.CanUpdateModel, CanFixModel, and CanCreateModel -- Interfaces for controlling Model access.
exception.ErrorFix	--Supporting methods called from AutoException
exception.AutoException	--Custom exception handler.

The driver with main is located at src/tester/driver2.java. 
The design diagram is Main.jpg

Here are errors and codes used in this version:

10404 file not found
10206 missing element in option line 
102061 missing element in option set line
102062 number not in correct format --used for both OptionSet and Option lines
102063 option set count not in correct format
102064 model name missing

After one or more AutoException is thrown from within FileIO's buildAutoModelObject(), FileIO will try to
write the Model with changes to a new plain text input file titled "fixed<brokenfilename.txt>." 

Note:
Some of the error-fixes may not be considered true self-healing. When a value is missing, the app goes 
to the console to ask for a new value.  I've learned on the job that sometimes a developer defaulting to a 
value can go missed by QA and cost thousands in production. My approach is bringing these errors to light, 
especially missing prices. 


