#John Curran
#CSCI 481
#Data Mining
#Homework 1

from math import*
import sys
from numpy import *

of = open('assign1-jwcurran.txt', 'w')

# get data into a numpy matrix called data
data = array

flag = 0

for i in open(sys.argv[1]):

    x = i.split(',')
    x.pop()
    
    for j in range(len(x)):
        x[j] = float(x[j])

    if flag == 0:
        data = x
        flag = 1
    else:
        data = vstack((data,x))
#

# (1) Compute the multivariate mean vector

avVector = mean(data, axis = 0)
print 'The multivariate mean vector is:\n\n' + array_repr(avVector) + '\n\n'
of.write('The multivaraite mean vector is:\n\n')
of.write(array_repr(avVector))
of.write('\n\n')

#

# (2) Compute the sample covariance matrix as inner products between the columns of the centered data matrix(see equation 2.31). Verify that your answer matches the one using numpy.cov function.

centeredData = data - avVector
dataCov = cov(centeredData,rowvar=0)
myDataCov = dot(centeredData.transpose(1,0),centeredData)/(len(centeredData)-1)

print('The covariance matrix via numpy is:\n\n' + array_repr(dataCov) + '\n\n')
print('The Covariance matrix that I calculated is:\n\n' + array_repr(myDataCov) + '\n\n')
of.write('The covariance matrix via numpy is:\n\n')
of.write(array_repr(dataCov))
of.write('\n\n')
of.write('The covariance that I calculated is:\n\n')
of.write(array_repr(myDataCov))
of.write('\n\n')

#

# (3) Which attribute has the largest variance, and which has the smallest variance? Print these values.

dataVar = var(data, axis=0)
myDataVar = mean((data - avVector)*(data - avVector),axis = 0)
minVarIndex = dataVar.argmin(axis=0)
maxVarIndex = dataVar.argmax(axis=0)

print 'The variable with the largest variance is variable '+ str(maxVarIndex) +' and its value is ' + str(dataVar[maxVarIndex])
print 'The variable with the smallest variance is variable '+ str(minVarIndex) + ' and its value is ' + str(dataVar[minVarIndex])


of.write('The variable with the largest variance is variable '+ str(maxVarIndex) +' and its value is ' + str(dataVar[maxVarIndex]))
of.write('\n\n')
of.write('The variable with the smallest variance is variable '+ str(minVarIndex) + ' and its value is ' + str(dataVar[minVarIndex]))
of.write('\n\n')

#

# (4) Which pair of attributes has the largest covariabce and which pair of attributes has the smallest covariance? Print these values.

maxCov = 0
for i in range(len(dataCov)):
	for j in range(len(dataCov[i])):
		if fabs(dataCov[i][j]) > maxCov and i != j:
			maxCov = fabs(dataCov[i][j])
			maxCovIndexI = i
			maxCovIndexJ = j
		
print 'The variables with the largest covariance are variables ' + str(maxCovIndexI) + ' and ' + str(maxCovIndexJ) + ' and its value is ' + str(dataCov[maxCovIndexI][maxCovIndexJ])


minCov = 2147483647
for i in range(len(dataCov)):
	for j in range(len(dataCov[i])):
		if fabs(dataCov[i][j]) < minCov and i != j:
			minCov = fabs(dataCov[i][j])
			minCovIndexI = i
			minCovIndexJ = j

print 'The variables with the smallest covariance are variables ' + str(minCovIndexI) + ' and ' + str(minCovIndexJ) + ' and its value is ' + str(dataCov[minCovIndexI][minCovIndexJ])

of.write('The variables with the largest covariance are variables ' + str(maxCovIndexI) + ' and ' + str(maxCovIndexJ) + ' and its value is ' + str(dataCov[maxCovIndexI][maxCovIndexJ]))
of.write('\n\n')
of.write('The variables with the smallest covariance are variables ' + str(minCovIndexI) + ' and ' + str(minCovIndexJ) + ' and its value is ' + str(dataCov[minCovIndexI][minCovIndexJ]))
of.write('\n\n')

#

# (5) Compute the dominant eigenvalue and eigenvector of the covariance matrix via the power-iteratrion method.

eigenvalues, eigenvectors = linalg.eig(dataCov)


myEigenVector = ones((len(dataCov),1))
myEigenVectorPrev = ones((len(dataCov),1))

converge = 1

while converge > 0.000000001:
	myEigenVectorPrev = myEigenVector
	myEigenVector = dot(dataCov,myEigenVector)
	myEigenVector /= float(max(myEigenVector))
	converge = myEigenVector - myEigenVectorPrev
	sum = 0
	for j in range(len(converge)):
		sum += converge[j] * converge[j]
	converge = sqrt(sum)


myEigenValue = dot(transpose(dot(dataCov,myEigenVector)),myEigenVector)/(dot(transpose(myEigenVector),myEigenVector))

myEigenVector /= linalg.norm(myEigenVector)



print 'The eigenvalues via numpy are:\n\n' + array_repr(eigenvalues) + '\n\n'
of.write('The eigenvalues via numpy are:\n\n' + array_repr(eigenvalues) + '\n\n')
print 'The eigenvectors via numpy are:\n\n' + array_repr(eigenvectors) + '\n\n'
of.write('The eigenvectors via numpy are:\n\n' + array_repr(eigenvectors) + '\n\n')
print 'The eigenvalue that I calculated is:\n\n' + array_repr(myEigenValue) + '\n\n'
of.write('The eigenvalue that I calculated is:\n\n' + array_repr(myEigenValue) + '\n\n')
print'The eigenvector that I calculated is:\n\n' + array_repr(myEigenVector) + '\n\n'
of.write('The eigenvector that I calculated is:\n\n' + array_repr(myEigenVector) + '\n\n')


#

# (6) Compute the projection of data points along the first two dominant eigenvectors, and compute the variance of those data points.

#projection of data points onto dominant eigenvector 1
for i in range(len(data)):
	if i ==0:
		projectedData1 = transpose(dot(data[i], myEigenVector)*myEigenVector)
	else:
		projectedData1 = vstack((projectedData1,transpose(dot(data[i], myEigenVector)*myEigenVector)))

projectedDataAverage1 = mean(projectedData1,axis = 0)

projectedDataVar1 = mean((projectedData1 - projectedDataAverage1)*(projectedData1 - projectedDataAverage1),axis = 0)



#projection of data points onto dominant eigenvector2

eigenVector2 = array([transpose(eigenvectors)[1]])
eigenVector2 =  transpose(eigenVector2)

for i in range(len(data)):
	if i ==0:
		projectedData2 = transpose(dot(data[i], eigenVector2)*eigenVector2)
	else:
		projectedData2 = vstack((projectedData2,transpose(dot(data[i], eigenVector2)*eigenVector2)))

projectedDataAverage2 = mean(projectedData2,axis = 0)

projectedDataVar2 = mean((projectedData2 - projectedDataAverage2)*(projectedData2 - projectedDataAverage2),axis = 0)

print 'The projection of the data points onto the first eigenvector is:\n\n' + array_repr(projectedData1) + '\n\n' + 'The variance of those data points is:\n\n' + array_repr(projectedDataVar1) + '\n\n' 

of.write('The projection of the data points onto the first eigenvector is:\n\n' + array_repr(projectedData1) + '\n\n' + 'The variance of those data points is:\n\n' + array_repr(projectedDataVar1) + '\n\n')

print 'The projection of the data points onto the second eigenvector is:\n\n' + array_repr(projectedData2) + '\n\n' + 'The variance of those data points is:\n\n' + array_repr(projectedDataVar2) + '\n\n' 

of.write('The projection of the data points onto the second eigenvector is:\n\n' + array_repr(projectedData2) + '\n\n' + 'The variance of those data points is:\n\n' + array_repr(projectedDataVar2) + '\n\n')

#








