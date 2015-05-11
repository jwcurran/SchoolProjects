#John Curran
#CSCI 481
#Data Mining
#Homework 3
#Question 1 - Model Generator - Naive Bayes 

import os
import sys
from numpy import *
from math import *

cmd = sys.argv
filename = cmd[1]
targetdata = []
alldata = []
classdata = []
targetitems = []
priorprobs = []
u = []
sigma = []

if len(cmd) != 2:
	print "Please read README.txt and re-enter your command line arguments."
	sys.exit()

if not os.access(filename,os.R_OK):
	print "The file name/path given as an argument either does not exist, or is inaccessible."
	sys.exit()

of = open('naive_bayes_MODEL-' + filename, 'w')

for i in open(filename):
	x = i.split(',')	
	y = []
	for j in range(len(x)):
		if not j == len(x) - 1:
			x[j] = float(x[j])
		else:
			x[j] = x[j].replace('\n', '')
		y.append(x[j])	
	
	alldata.append(y)
	
	if(targetitems.count(x[len(x)-1]) == 0):
		targetitems.append(x[len(x)-1])
		x.pop()
		targetdata.append([x])
	else:
		index = targetitems.index(x[len(x)-1])
		x.pop()
		targetdata[index].append(x)				




data = vstack(alldata)


for i in range(len(targetdata)):
	classdata.append([targetdata[i]])

for i in range(len(classdata)):
	classdata [i] = vstack(classdata[i])	
	u.append(mean(classdata[i], axis = 0))	
	priorprobs.append(float(len(classdata[i]))/len(alldata))	
	sigma.append(cov(classdata[i].transpose(1,0)))
	for k in range(len(sigma[i])):
##Here is the main difference between the full bayes and the naive bayes. The covariance matrix contains only the variance data, all of the covariance data is 0.
		for l in range(len(sigma[i][k])):
			if k != l:
				sigma[i][k][l] = 0;
				

for i in range(len(targetitems)):
	of.write(str(priorprobs[i]) + '\n')	
	of.write(targetitems[i] + '\n')
	for j in range(len(u[i])):
		of.write(str('{0:.2f}'.format(u[i][j])))
		if j != len(u[i])-1:
			of.write(',')
	of.write('\n')
	
	for j in range(len(sigma[i])):
		for k in range(len(sigma[i][j])):
			of.write(str('{0:.2f}'.format(sigma[i][j][k])))
			if j*k != 9:			
				of.write(',')
	of.write('\n')



