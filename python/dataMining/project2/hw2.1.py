#John Curran
#CSCI 481
#Data Mining
#Homework 2

import sys
from time import *
import os

#This is the pattern class. This class' constructor takes in a pattern and the set of corresponding tidsets.
#It then calculates the support from those tidsets.

class Pattern:
	def __init__(self,pattern,tidsets):
		self.pattern = pattern
		self.tidset = []
		self.extensions = []	
		self.support = 0
		self.intersectionTid(tidsets)
		self.calcSupport()
				
				
	def intersectionTid(self,tidsets):
		self.temp = tidsets
		
		if len(tidsets) == 1:
			self.tidset.append(tidsets[0])
			return
		for i in range(len(tidsets[0])):
			self.intersectionFlag = 0			
			for j in range(len(self.temp)):
				if self.temp[j].count(tidsets[0][i]) > 0:
					self.intersectionFlag = 1
				else: 
					self.intersectionFlag = 0
			if self.intersectionFlag == 1:
				self.tidset.append(tidsets[0][i])
			
	def appendTidset(self,tid):
		self.tidset.append(tid)
		self.calcSupport()

	def calcSupport(self):
		self.support = len(self.tidset)
	
	def extend(self,extension):
		self.extensions.append(extension)
	
	def getSupport(self):
		return self.support
	
	def getTidset(self):
		return self.tidset
	
	def getPattern(self):
		return self.pattern

	def getExtensions(self):
		return self.extensions

#This is the frequent pattern class, the constructor accepts a list of the level one frequent patterns and the
#number of transactions.
#From there it generates a root node with the empty set pattern and tidset containing every transaction.
#It then adds the level one frequent patterns to the root and calls the eclat algorithm to construct the tree
#of all frequents with support greater than minsupport and keeps track of the number of such patterns
class FrequentPatternStore:
	def __init__(self, level1, numTransactions):
		self.rootTidset = []
		for i in range(numTransactions):
			self.rootTidset.append(i + 1)
		self.root = Pattern([],[self.rootTidset])
		self.frequentCount = 0;
		for i in range(len(level1)):
			self.root.extend(level1[i])
			self.frequentCount += 1

		self.eclat(self.root)			
		
	def getSiblings(parent,pattern):
		if not parent.getExtensions():
			return 0
		if parent.getExtensions().count(pattern) > 0:
			return parent.getExtensions
		for i in range(len(parent.getExtensions())):
			getSiblings(parent.getExtensions()[i])
	
	def getRoot(self):
		return self.root

	def eclat(self, root):
		if not root.getExtensions():
			return;		
		level = root.getExtensions()
		for i in range(len(level) - 1):
			for j in range((i+1),len(level)):
				temp = []
				for k in range(len(level[i].getPattern())):
					temp.append(level[i].getPattern()[k])
				for k in range(len(level[j].getPattern())):
					if temp.count(level[j].getPattern()[k]) == 0:
						temp.append(level[j].getPattern()[k])
				newPat = Pattern(temp,[level[i].getTidset(),level[j].getTidset()])
				if newPat.getSupport() >= minSupport:
					level[i].extend(newPat)
					self.frequentCount += 1
		for i in range(len(level)):
			self.eclat(level[i])
		
					
	def getFrequentCount(self):
		return self.frequentCount

	def printFrequentPatterns(self):
		self.printTree(self.root)
	
	def printTree(self, root):
		if not root.getExtensions():
			print root.getPattern()
			return
		for i in range(len(root.getExtensions())):
			self.printTree(root.getExtensions()[i])
		print root.getPattern()		

#This is the DbReader class, it reads in data from a file and finds the level one frequent patterns
#and keeps track of the number of transactions				
class DbReader:
	def __init__(self):
		self.patterns = []
		self.tid = 0
		for i in open(filename):
			x = i.split(' ')
			x.pop(0)
			self.tid += 1
			for j in range(len(x)):
				x[j] = int(x[j])
				self.found = 0
				for k in range(len(self.patterns)):
					if self.patterns[k].getPattern().count(x[j]) > 0:
						self.patterns[k].appendTidset(self.tid)
						self.found = 1
				
				if self.found == 0:
					self.patterns.append(Pattern([x[j]],[self.tid]))

		i = 0
		while i < len(self.patterns):
			if self.patterns[i].getSupport() < minSupport:
				self.patterns.remove(self.patterns[i])
			else:
				i += 1

	def displayPatterns(self):
		for i in range(len(self.patterns)):
			print self.patterns[i].getPattern()
			print self.patterns[i].getTidset()
			print self.patterns[i].getSupport()

	def getLevel1(self):
		return self.patterns
	
	def getNumTransactions(self):
		return self.tid


#The main part of the program. This takes in arguments form the command line, and validates the input such that it can operate properly.
#It checks that there are the proper number of arguments, that the file exists on the system, that the proper flags are used, and finally
#that the minsupport value is a positive integer. The program keeps track of the time needed to read in the data and find the frequent
#patterns. The user has the option of showing or not showing the frequent patterns.
cmd = sys.argv
if sys.argv.count('-f') == 0 or sys.argv.count('-s') == 0 or len(sys.argv) != 5:
	print "Please read README.txt and re-enter your command line arguments."
	sys.exit()
findex = cmd.index('-f') + 1
sindex = cmd.index('-s') + 1
filename = cmd[findex]
if not os.access(filename,os.R_OK):
	print "The file name/path given as an argument either does not exist, or is inaccessible."
	sys.exit()
minSupport = int(cmd[sindex])
if minSupport < 1:
	print "Minimum support must be a positive integer."
	sys.exit()
time1 = time()
reader = DbReader()
frequent = FrequentPatternStore(reader.getLevel1(), reader.getNumTransactions())
time2 = time()
print "The time to complete the task was: ", time2 - time1, " seconds."
print "The number of patterns with support greater than ", minSupport, " is ", frequent.getFrequentCount(), ". (excluding the empty set)"
yn = ''
while yn != 'y' and yn != 'n' and yn != 'Y' and yn != 'N':
	yn = raw_input("Print the frequent patterns? (y/n): ") 
if yn == 'y' or yn == 'Y':
	frequent.printFrequentPatterns()	



		


