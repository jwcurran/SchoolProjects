Frequent Pattern Miner(R) version 1.1 (FREEWARE) 09/28/2013

ABOUT
------
Frequent Pattern Miner(R) version 1.1 is a program designed to read a list of transactions from a file 
and find the frequent patterns of items occuring within those transactions. The program keeps track of how 
much time it takes to complete the mining based on a user given minimum support value. This program utilizes
the recursive eclat algorithm to determine the frequent patterns of items within the transactions. The user 
has the option of displaying the frequent patterns, or just seeing the default output which is the number of 
patterns that have a support greater than or equal to the minimum support value supplied by the user.

GENERAL USAGE NOTES
--------------------
- This program is a Python(R) script
- This program will run on any platform with a python virtual machine
- Calling this program should occur in the form "python hw2.1.py -f filename -s minimumSupport"
	- The '-f' tag precedes the filename
		- Filename must not contain a space character
	- The '-s' tag precedes the minimum support value, a positive integer greater than 0
	- The order of the previous two items and their respective arguments does not matter
	- The entire path of the input file must be given if it is a separate directory from the python script
- Input data files should be formated with numeric values separated by spaces
	- Each Line in an input file represents a transaction
	- Each transaction should begin with the number of items in the transaction itemset
	- No transaction ID is necessary, one will be generated.
- The total time to read the file and execute will be displayed in seconds
- You will be given the option of displaying the frequent patterns of items, input y to display the patterns
	or input n to exit the program

---------------------------------------------------------------------------------------------------------------
***************************************************************************************************************

SUPPORT
-------
- Frequent Pattern Miner can be reached at:
	
	E-Mail: jwcurran@iupui.edu
	website: frequentpatternminer.com (coming soon)

Copyright 2013 John Curran. All rights reserved. Frequent Pattern Miner(R) and its use are subject to a license agreement 
and are also subject to copyright, trademark, patent and/or other pertainent laws. Refer to frequentpatternminer.com/legal 
for additional information about usage rights. All other brand and product names are trademarks or registered.


