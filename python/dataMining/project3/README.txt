2Way Bayesian Classifier(R) version 1.1 (FREEWARE) 10/25/2013

ABOUT
------
2Way Bayesian Classifier(R) version 1.1 is a set of four programs. Two programs for Full Bayes Classification and two programs for Naive Bayes Classification. One of which is a training program that generates a model file from training data which contains the prior probabilities of the class labels, the class labels themselves, the mean vector for each class label, and the covariance matrix for each of the class labels. The second of the two programs uses the model file to classifiy new data. The testing program will output a file which contains the actual and predicted class labels, the confusion matrix and the accuracy,precision,recall and F-Score for each label. The useage notes for these programs will be the same except for names of the scripts and corresponding output files. The program reads in a text file of a format specified in the usuage notes.

GENERAL USAGE NOTES
---------------------
- This program is a Python(R) script
- This program will run on any platform with a python virtual machine
- The input data should be formatted with the entries in rows.
	- d1,d2,...,dk,classlabel
		- d1 through dk are real numeric fields
		- classlabel is a string containing the classification
- The file containing the input data must be in plain text format (.txt file extension)

- Calling the Full Bayes Model Generator Training Program:
	-"python train.full.bayes.py filename"
	-filename is the name of the file containing the training data
	-The entire path of the input file must be given if it is a separate directory from the python script
	-output model file is named "full_bayes_MODEL-filename"

- Calling the Full Bayes Model Testing Program:
	- Must be called AFTER the full bayes model generator program(see previous).
	-"python test.full.bayes.py -m modelfile -t testdata"
	- The '-m' tag precedes the model file name from the model generator "full_bayes_MODEL-filename" 
	- The '-t' tag precedes the file name of the test data
		-test data must be in same format as training data(including class labels).
		- this format is:
			- d1,d2,...,dk,classlabel
				- d1 through dk are real numeric fields
				- classlabel is a string containing the classification
	- Output file containing predicitons and statistics is named "full_bayes_PREDICTIONS-filename"

- Calling the Naive Bayes Model Generator Training Program:
	-"python train.naive.bayes.py filename"
	-filename is the name of the file containing the training data
	-The entire path of the input file must be given if it is a separate directory from the python script
	-output model file is named "naive_bayes_MODEL-filename"	

- Calling the Naive Bayes Model Testing Program:
	- Must be called AFTER the naive bayes model generator program(see previous).
	-"python test.naive.bayes.py -m modelfile -t testdata"
	- The '-m' tag precedes the model file name from the model generator "naive_bayes_MODEL-filename" 
	- The '-t' tag precedes the file name of the test data
		-test data must be in same format as training data(including class labels).
		- this format is:
			- d1,d2,...,dk,classlabel
				- d1 through dk are real numeric fields
				- classlabel is a string containing the classification
	- Output file containing predicitons and statistics is named "naive_bayes_PREDICTIONS-filename"

- All output files are plain text files (.txt file extension)

---------------------------------------------------------------------------------------------------------------
***************************************************************************************************************

SUPPORT
-------
- 2Way Bayesian Classifier can be reached at:
	
	E-Mail: jwcurran@iupui.edu
	website: 2WayBayesianClassifier.com (coming soon)

Copyright 2013 John Curran. All rights reserved. 2Way Bayesian Classifier and its use are subject to a license agreement 
and are also subject to copyright, trademark, patent and/or other pertainent laws. Refer to frequentpatternminer.com/legal 
for additional information about usage rights. All other brand and product names are trademarks or registered.








 
