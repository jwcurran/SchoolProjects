#John Curran
#CSCI 481 Data Mining
#Homework 4
#Question 1: K-Means Clustering

import sys
import os
import math
from numpy import *
from random import *
from time import *

##defined classes, instance keeps track of its ID, the cluster it belongs to, and its coordinates

class Instance:
	def __init__(self, coords, index):
		self.index = index
		self.coords = coords
	def setCluster(self, clusterNum):
		self.clusterNum = clusterNum
	def getCluster(self):
		return self.clusterNum
	def getCoords(self):
		return array(self.coords)
	def getID(self):
		return self.index

##Cluster class keeps track of its mean, its previous mean, its members as both coordinates and instance objects. It can add members, clear members, compute mean of members, compute SSE. For clear
#members method, reassignment of variable to empty list is sufficient as Python is a garbage collecting language.

class Cluster:
	def __init__(self,initMean):
		self.mean = initMean.getCoords()
		self.members = []
		self.instances = []
		self.previousMean = []
	def addMember(self,instance):
		self.instances.append(instance)
		self.members.append(instance.getCoords())
	def clearMembers(self):
		self.instances = []
		self.members = []
	def computeMean(self):
		if len(self.members) != 0:		
			self.previousMean = self.mean
			#vstack(self.members)
			self.mean = mean(self.members,axis=0)
			self.change = linalg.norm(self.mean - self.previousMean)
		else:
 			self.change = 0		
		
		return self.change
	def getMean(self):
		return array(self.mean)
	def computeSSE(self):
		self.sse = 0
		for i in range(len(self.members)):
			self.sse = self.sse + pow(linalg.norm(self.members[i] - self.mean),2)
	def getSize(self):
		return len(self.members)
	def getSSE(self):
		return self.sse
	def getMembers(self):
		return self.members
	def getMemberIDs(self):
		self.memberID = []
		for i in range(len(self.instances)):
			self.memberID.append(self.instances[i].getID())
		return self.memberID
		


##############################   Main program #######################################
# Take command line arguments and validate them, they must be given in a specific order as defined in README.txt
cmd = sys.argv

##basic user input validation of number of inputs and making sure files exist

if len(cmd) != 3 and len(cmd) != 4:
	print "The number of arguments is not correct. Please read README.txt and then retry execution of K-Means."
	sys.exit()

filename = cmd[1]
if not os.access(filename,os.R_OK):
	print "The data file name/path given as an argument either does not exist, or is inaccessible. Please check that the file exists and/or read README.txt"
	sys.exit()

k = int(cmd[2])
epsilon = 0.001
data = []
clusters = []
isConverged = False
data2 = []
initialMeans = []

lncnt = 0
for i in open(filename):
	x = i.split(',')
	for j in range(len(x)):
		x[j] = float(x[j])
	data.append(Instance(x,lncnt))
	data2.append(x)	
	lncnt += 1


#Initialize clusters based on the cmd input, can accept a centroid file or will randomly select centroids to generate initial means. Basic data validation to make sure #desired clusters is equal
#to the #centroids in centroid file. Also makes sure that the centroid file exists.

if len(cmd) == 3:
	for i in range(k):
		initialMeans.append(randint(0,len(data)-1))
		clusters.append(Cluster(data[initialMeans[i]]))
else:
	centroidFile = cmd[3]
	if not os.access(centroidFile,os.R_OK):
		print "The centroid file name/path given as an argument either does not exist, or is inaccessible. Please check that the file exists and/or read README.txt"
		sys.exit()
	for j in open(centroidFile):
		initialMeans.append(int(j))
		clusters.append(Cluster(data[int(j)]))
	if k != len(initialMeans):
		print "The number of desired clusters does not match the number of initial centroids in the centroidfile. Please read README.txt"
		sys.exit()	


##                                         K-Means algorithm

t = 0
t1 = time()
while isConverged != True:
	t = t + 1
	for i in range(len(data)):
		minDistance = sys.maxint
		cid = -1
		for j in range(k):
			clusters[j].getMean()
			if pow(linalg.norm(data[i].getCoords() - clusters[j].getMean()),2) < minDistance:
				minDistance = pow(linalg.norm(data[i].getCoords() - clusters[j].getMean()),2)
				cid = j
		clusters[cid].addMember(data[i])
		data[i].setCluster(cid)
	
	normSum = 0	
	for i in range(k):
		normSum = normSum + clusters[i].computeMean()
	if normSum <= epsilon:
		isConverged = True
	else:
		for i in range(k):
			clusters[i].clearMembers()			
t2 = time()


#Sum the SSE of the individual clusters to get the overall SSE score of the clustering
SSE = 0
for i in range(k):
	clusters[i].computeSSE()
	SSE = SSE + clusters[i].getSSE()


#Print the Results	 

print "The number of data points in input file: ", len(data), ", Dimension: ", len(data[0].getCoords()), ", Number of clusters desired: ", k
if len(cmd) == 3:
	print "Initial means: ", initialMeans, " (randomly chosen)"
else:
	print "Initial means: ", initialMeans, " (from centroid file)"
print "The number of iterations for convergence: ", t
print "SSE =  ", SSE, " , Time: ", (t2-t1)*1000, "ms\n"

for i in range(k):
	print "Cluster : ", i, " Size: ", clusters[i].getSize()
	print "Cluster ", i, "'s mean: ", clusters[i].getMean()
	print "Member IDs: {", clusters[i].getMemberIDs(), "}"	
	print "Cluster ", i, " SSE = ", clusters[i].getSSE(), "\n"		
		

#	print i, " ", clusters[i].getMean(), " : ", clusters[i].getMemberIDs(), " SSE = ", clusters[i].getSSE() 


#Calculate the purity of the clustering. For this calculation, the true classifcation of the data instances must be known so this is specific to the given input file.
purity = 0
for i in range(k):
	imembers = clusters[i].getMemberIDs()
	sums = [0,0,0] 	
	for j in range(len(imembers)):
		if imembers[j] < 50:
			sums[0] += 1
		elif imembers[j] < 100:
			sums[1] += 1
		else:
			sums[2] += 1
	maxmem = 0
	for j in range(len(sums)):
		if sums[j] > maxmem:
			maxmem = sums[j]
	purity += maxmem

purity = float(purity)/float(len(data))
		
print "Purity = ", purity





	
