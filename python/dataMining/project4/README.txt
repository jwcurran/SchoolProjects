K-Means Clusters (R) version 1.1 (FREEWARE) 12/1/2013

ABOUT
------
K-Means Clusters (R) version 1.1 is a program which reads a dataset of any number of data instances of any number of comma separated dimensions. It accepts an input file, a desired integer number of clusters and (optionally) a file containing the IDs of the initial means for the clusters. The program works by assigning all of the points to the cluster with the closest mean point. It then recomputes the mean for each cluster and reassigns the points. The program continues in this manner until the sum of the normalized change in all the means is less than some epsilon (0.001). The program will then output information and statistics about the data and how it is clustered.

GENERAL USAGE NOTES
----------------------
-This program is a Python(R) script
-This program will run on any platform with a python virtual machine and the numpy library installed.
-The input data should formatted with one instance on each line and comma separated dimensions.
	-d11,d12,....,d1d
	-d21,d22,....,d2d
		-dn1 through dnd are the dimensions, real numeric fields of the instances.
-The file containing the input data must be in plain text format (.txt file extension)
-The (optional) file containing the initial centroid ids should have one id per line.
	-If this file is not given, IDs will be randomly generated (one for each cluster). 
-The id is assumed as the position of the instance within the input file.


-Calling the K-Means Clusters:

	-"python hw4.1.py inputfilename #clusters"
		-OR
	-"python hw4.1.py inputfilename #clusters centroidfilename"

	-The inputfilename is the name of the file contianing the input data
	-The centroidfilename is the name of the file containg the IDs of the initial centroids
		-the IDs of the initial centroids are assumed to be the line number of the desired data instance to be used in the input file
	-File extensions for the input files must always be given
	-If the input files are in a different directory, the entire filepath must be given.
 
	-THE COMMAND LINE ARGUMENTS MUST BE GIVEN IN THE ORDER SHOWN ON LINE 20 OR LINE 22 OF THIS DOCUMENT


SUPPORT
---------
-K-Means Clusters can be reached at:
	E-Mail: jwcurran@iupui.edu
	website: kmeansclusters.com (coming soon)

COPYRIGHT 2013 John Curran. All rights reserved. K-Means Clusters (R) and its use are subject to a license agreement and are also subject to copyright, trademark, patent and/or other pertainent laws. Refer to kmeansclusters.com/legal for additional information about usage rights. All other brand and product names are trademarks or registered.


