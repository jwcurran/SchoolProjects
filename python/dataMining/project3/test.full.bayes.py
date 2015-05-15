#John Curran
#CSCI 481
#Data Mining
#Homework 3
#Question 2 - Full Bayes Test

import os
import sys
from math import *
from numpy import *
from numpy.linalg import *

cmd = sys.argv
targetitems = []
priorprobs = []
u = []
sigma = []

if sys.argv.count('-m') == 0 or sys.argv.count('-t') == 0 or len(sys.argv) != 5:
	print "Please read README.txt and re-enter your command line arguments."
	sys.exit()
mindex = cmd.index('-m') + 1
tindex = cmd.index('-t') + 1
modelfile = cmd[mindex]
if not os.access(modelfile,os.R_OK):
	print "The model file name/path given as an argument either does not exist, or is inaccessible."
	sys.exit()

testfile = cmd[tindex]
if not os.access(modelfile,os.R_OK):
	print "The test file name/path given as an argument either does not exist, or is inaccessible."
	sys.exit()

of = open('full_bayes_PREDICTIONS-' + testfile, 'w')


k = 0
for i in open(modelfile):
	if k == 0:
		priorprobs.append(float(i))
		k += 1
	elif k == 1:
		i = i.replace('\n', '')
		targetitems.append(i)
		k += 1
	elif k == 2:
		x = i.split(',')
		for j  in range(len(x)):
			x[j] = float(x[j])
		u.append(x)
		k += 1
	elif k == 3: 
		y = []
		cov = []
		m = 0
		x = i.split(',')
		for j in range(len(x)):
			if not j == len(x) - 1:
				x[j] = float(x[j])
			else:
				x[j] = float(x[j].replace('\n', ''))
		
		for j  in range(len(x)):
			if (j+1)%(len(u[0])) != 0:
				y.append(x[j])
			else:
				y.append(x[j])				
				cov.append(y)
				y = []	
		sigma.append([cov])
		k = 0
sigma = vstack(sigma)
u = array(u)

actual = []
predictions = []
y = []
for i in open(testfile):
	x = i.split(',')	
	for j in range(len(x)):
		if not j == len(x) - 1:
			x[j] = float(x[j])
		else:
			x[j] = x[j].replace('\n', '')
	actual.append(x.pop())
	
	f = []
	for j in range(len(targetitems)):
		f.append((1/(dot(pow(sqrt(2*pi),len(u[j])),sqrt(det(sigma[j]))))*exp(-dot(dot((x-u[j]),inv(sigma[j])),(x-u[j]))/2))*priorprobs[j])
	y.append(f)
	
y = vstack(y)

for i in range(len(y)):
	predictions.append(targetitems[y[i].argmax()])

confusion = zeros(shape = (len(targetitems),len(targetitems)))
truepos = zeros(shape = (len(targetitems)))
falsepos = zeros(shape = (len(targetitems)))
trueneg = zeros(shape = (len(targetitems)))
falseneg = zeros(shape = (len(targetitems)))
accuracy = []
precision = []
recall = []
fscore = []

for i in range(len(y)):
	for j in range(len(targetitems)):
		for k in range(len(targetitems)):
			if j == targetitems.index(actual[i]) and k == targetitems.index(predictions[i]):
				confusion[j][k] += 1
for i in range(len(confusion)):
	for j in range(len(confusion[0])):
		if i == j:
			truepos[i] = confusion[i][j]
			for k in range(len(confusion)):
				for l in range(len(confusion[0])):
					if k != i and l != i:
						trueneg[j] += confusion[l][k]
		else:
			falseneg[i] += confusion[i][j] 
			falsepos[j] += confusion[i][j]

for i in range(len(truepos)):
	accuracy.append((truepos[i]+trueneg[i])/len(predictions))
	precision.append(truepos[i]/(truepos[i]+falsepos[i]))
	recall.append(truepos[i]/(truepos[i]+falseneg[i]))
	fscore.append(2*precision[i]*recall[i]/(precision[i]+recall[i]))



of.write('actual\t\t\t\tpredicition\n\n')
for i in range(len(actual)):
	of.write(actual[i] + '\t\t\t' + predictions[i] + '\n')	
of.write(array_repr(confusion))
of.write('\n\n')

for i in range(len(targetitems)):
	of.write('\n#' + targetitems[i] + '\n')
	of.write('-> Accuracy = '+str(accuracy[i])+'\n')	
	of.write('-> Precision = '+str(precision[i])+'\n')
	of.write('-> Recall = ' + str(recall[i])+'\n')
	of.write('-> FScore = ' + str(fscore[i])+'\n')
	



	



	


	

		
		




