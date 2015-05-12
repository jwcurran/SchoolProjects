/************************************************************|~~~~~~~~~~~~~~~~|****************************************************************
*************************************************************| EDGE DETECTION |****************************************************************
*************************************************************|  John Curran   |****************************************************************
*************************************************************|~~~~~~~~~~~~~~~~|****************************************************************
**																																			 **
**	CSCI 435, Edge Detection Program that will convert a 3 channel grayscale image saved from photoshop to a one channel				     ** 
**	grayscale image and then perform various filtering convolutions on the pixels. Every type of filter will be computed using a different   **
**	function which is inefficient for the exact project requirements, but lends itself to easy implementation of greater flexibility by the  **
**	end user so that he/she can select which filter they would like to apply to the image. 													 **
**																																			 **
**********************************************************************************************************************************************/


//************************************************************** INCLUDES ******************************************************************//
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cmath>
using namespace std;

//********************************************************* PREPROCESSOR CONSTANTS *********************************************************//
#define WIDTH 500

//************************************************************ PIXEL STRUCTURE *************************************************************//
struct pixel
{
	unsigned char red;
	unsigned char green;
	unsigned char blue;
};

//************************************************************ GLOBAL VARIABLES ***********************************************************//
ifstream originalFile;
ofstream filteredFile;

vector<pixel> originalPixels; 
vector<unsigned char*> grayscalePixels;
vector<unsigned char*> hdiffPixels3;
vector<unsigned char*> hdiffPixels5;
vector<unsigned char*> vdiffPixels3;
vector<unsigned char*> vdiffPixels5;
vector<unsigned char*> gradientPixels3;
vector<unsigned char*> gradientPixels5;
string fileName;

//************************************************************ OUTPUT FILE NAMES **********************************************************//
const string rawName = "grayscale.raw";
const string blurName3 = "motionBlur3x3.raw";
const string blurName5 = "motionBlur5x5.raw";
const string gaussName3 = "gaussianBlur3x3.raw";
const string gaussName5 = "gaussianBlur5x5.raw";
const string hdiffName3 = "horizontalDifferentiated3x3.raw";
const string vdiffName3 = "verticalDifferentiated3x3.raw";
const string hdiffName5 = "horizontalDifferentiated5x5.raw";
const string vdiffName5 = "verticalDifferentiated5x5.raw";
const string gradientName3 = "gradient3x3.raw";
const string gradientName5 = "gradient5x5.raw";
const string threshName3 = "threshold3x3.raw";
const string threshName5 = "threshold5x5.raw";

//*********************************************************** FUNCTION PROTOTYPES *********************************************************//
void openFile(void);
void getImage(void);
void motionBlur(void);
void gaussianBlur(void);
void horizontalDifferentiate(void);
void verticalDifferentiate(void);
void gradient(void);
void threshold(void);
void filter(void);

//*********************************************************** FUNCTION MAIN *********************************************************//
void main()
{	
	openFile();
	getImage();
	motionBlur();
	gaussianBlur();
	horizontalDifferentiate();
	verticalDifferentiate();
	gradient();
	threshold();
}

//*********************************************************** FUNCTION openFile *********************************************************//
/*This function will ask the user to enter the file name of the file that they wish to filter. It has error checking for nonexistant files
The file must be a 3 channel grayscale raw image which is what photoshop natively saves to when a grayscale file is saved in .raw format*/
/*******************************************************************************************************************************************/

void openFile(void)
{
	while(!originalFile.is_open())
	{
		cout << "Please enter the file name (with extnesion) of the 3 channel grayscale image. " << endl;
		cin >> fileName;
		originalFile.open(fileName, ios::binary);
		
		if(!originalFile.is_open())
			cout << "Unable to open file " << fileName << ". Please try again." << endl; 
	}
	
}


//*********************************************************** FUNCTION getImage *********************************************************//
/*This function will read the 3 channel grayscale raw image file and save it into a pixel structure array vector which maintains the exact original
format of the image. It will then convert the file into a one channel grayscale raw image file and put this also into an unsigned character array
vector. The two input file stream will then be closed because all of the information is saved in the vectors.*/
/*******************************************************************************************************************************************/

void getImage(void)
{
	int i = 0;
	int j = 0;
	unsigned char* grayArray = new unsigned char[WIDTH];
	pixel temp;
	streampos endOriginal;
	streampos count;
				
	
	filteredFile.open(rawName, ios::binary);
	

	originalFile.seekg(0, ios::end);
	endOriginal = originalFile.tellg();
	originalFile.seekg(ios::beg);

	for(count = 0; count < endOriginal; count += 3)
	{
		temp.red = originalFile.get();
		temp.green = originalFile.get();
		temp.blue = originalFile.get();
	
		if(i < WIDTH - 1)
		{
			grayArray[i] = temp.red;
			i++;
		}
		else
		{
			grayArray[i] = '\n';
			grayscalePixels.push_back(grayArray);
			grayArray = new unsigned char[WIDTH];
			i = 0;
		}

		originalPixels.push_back(temp);
	}

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
			filteredFile << *(grayscalePixels[i] + j);
	}

	filteredFile.close();
	originalFile.close();
}


//*********************************************************** FUNCTION motionBlur *********************************************************//
/*This function will iterate through the vector containing the one channel grayscale raw pixels and convolve them with both a 3x3 and a 
5x5 diagonal averaging operator to achieve a motion blur filter. */
/*******************************************************************************************************************************************/

void motionBlur(void)
{
	int i = 0;
	int j = 0;
	int k = 0;
	int m = 0;
	unsigned int pixelSum = 0;
	unsigned int pixelAverage = 0;
	unsigned char filter3[3][3] = {{1,0,0},{0,1,0},{0,0,1}};
	unsigned char filter5[5][5] = {{1,0,0,0,0},{0,1,0,0,0},{0,0,1,0,0},{0,0,0,1,0},{0,0,0,0,1}};

	filteredFile.open(blurName3, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == grayscalePixels.size()-1 || j == 0 || j == WIDTH - 1)
				filteredFile << *(grayscalePixels[i] + j);
			else
			{
				for(k = -1; k < 2; k++)
				{
					for(m = -1; m < 2; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * filter3[k+1][m+1];
					}
				}
				pixelAverage = pixelSum/3;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
			}
		}
	}

	filteredFile.close();

	filteredFile.open(blurName5, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == 1 || i == grayscalePixels.size()-2 || i == grayscalePixels.size()-1 || j == 0 || j == 1 || j == WIDTH - 2 || j == WIDTH - 1)
				filteredFile << *(grayscalePixels[i] + j);
			else
			{
				for(k = -2; k < 3; k++)
				{
					for(m = -2; m < 3; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * filter5[k+2][m+2];
					}
				}
				pixelAverage = pixelSum/5;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
			}
		}
	}

	filteredFile.close();

}


//*********************************************************** FUNCTION gaussianBlur *********************************************************//
/*This function will iterate through the vector containing the one channel grayscale raw pixels and convolve them with both a 3x3 and a 
5x5 gaussian averaging operator to achieve a gaussian blur filter. */
/*******************************************************************************************************************************************/

void gaussianBlur(void)
{
	int i = 0;
	int j = 0;
	int k = 0;
	int m = 0;
	unsigned int pixelSum = 0;
	unsigned int pixelAverage = 0;
	unsigned char filter3[3][3] = {{1,2,1},{2,4,2},{1,2,1}};
	unsigned char filter5[5][5] = {{1,4,7,4,1},{4,16,26,16,4},{7,26,41,26,7},{4,16,26,16,4},{1,4,7,4,1}};

	filteredFile.open(gaussName3, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == grayscalePixels.size()-1 || j == 0 || j == WIDTH - 1)
				filteredFile << *(grayscalePixels[i] + j);
			else
			{
				for(k = -1; k < 2; k++)
				{
					for(m = -1; m < 2; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * filter3[k+1][m+1];
					}
				}
				pixelAverage = pixelSum/16;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
			}
		}
	}

	filteredFile.close();

	filteredFile.open(gaussName5, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == 1 || i == grayscalePixels.size()-2 || i == grayscalePixels.size()-1 || j == 0 || j == 1 || j == WIDTH - 2 || j == WIDTH - 1)
				filteredFile << *(grayscalePixels[i] + j);
			else
			{
				for(k = -2; k < 3; k++)
				{
					for(m = -2; m < 3; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * filter5[k+2][m+2];
					}
				}
				pixelAverage = pixelSum/273;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
			}
		}
	}

	filteredFile.close();
}


//*********************************************************** FUNCTION horizontalDifferentiate *********************************************************//
/*This function will iterate through the vector containing the one channel grayscale raw pixels and convolve them with both a 3x3 and a 
5x5 horizontal sobel operator to achieve horizontal edge detection filter. The values will be scaled between the values [0,255] and will be both outputted
to a file and stored in a vector for future use*/
/*******************************************************************************************************************************************/

void horizontalDifferentiate(void)
{
	int i = 0;
	int j = 0;
	int k = 0;
	int m = 0;
	unsigned char* hdiffArray = new unsigned char[WIDTH];
	int pixelSum = 0;
	int pixelAverage = 0;
	char hfilter3[3][3] = {{-1,-1,-1},{0,0,0},{1,1,1}};
	char hfilter5[5][5] = {{-1,-1,-1,-1,-1},{-2,-2,-2,-2,-2},{0,0,0,0,0},{2,2,2,2,2},{1,1,1,1,1}};
	
	filteredFile.open(hdiffName3, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == grayscalePixels.size()-1 || j == 0 || j == WIDTH - 1)
			{
				filteredFile << *(grayscalePixels[i] + j);
				hdiffArray[j] = *(grayscalePixels[i] + j);
			}
			else
			{
				for(k = -1; k < 2; k++)
				{
					for(m = -1; m < 2; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * hfilter3[k+1][m+1];
					}
				}
				pixelAverage = (255 + pixelSum/3)/2;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
				hdiffArray[j] = (unsigned char) pixelAverage;
			}
						
		}
		
		hdiffPixels3.push_back(hdiffArray);
		hdiffArray = new unsigned char[WIDTH];
		
	}

	filteredFile.close();

	filteredFile.open(hdiffName5, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == 1 || i == grayscalePixels.size()-2 || i == grayscalePixels.size()-1 || j == 0 || j == 1 || j == WIDTH - 2 || j == WIDTH - 1)
			{
				filteredFile << *(grayscalePixels[i] + j);
				hdiffArray[j] = *(grayscalePixels[i] + j);
			}
			else
			{
				for(k = -2; k < 3; k++)
				{
					for(m = -2; m < 3; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * hfilter5[k+2][m+2];
					}
				}
				pixelAverage = (255 + pixelSum/15)/2;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
				hdiffArray[j] = (unsigned char) pixelAverage;
			}
		}

		hdiffPixels5.push_back(hdiffArray);
		hdiffArray = new unsigned char[WIDTH];
	}

	filteredFile.close();
}


//*********************************************************** FUNCTION verticalDifferentiate *********************************************************//
/*This function will iterate through the vector containing the one channel grayscale raw pixels and convolve them with both a 3x3 and a 
5x5 vertical sobel operator to achieve vertical edge detection filter. The values will be scaled between the values [0,255] and will be
both outputted to a file and stored in another unsigned character pixel vector for future use*/
/*******************************************************************************************************************************************/

void verticalDifferentiate(void)
{
	int i = 0;
	int j = 0;
	int k = 0;
	int m = 0;
	int pixelSum = 0;
	int pixelAverage = 0;
	unsigned char* vdiffArray = new unsigned char[WIDTH];
	char hfilter3[3][3] = {{-1,0,1},{-1,0,1},{-1,0,1}};
	char hfilter5[5][5] = {{-1,-2,0,2,1},{-1,-2,0,2,1},{-1,-2,0,2,1},{-1,-2,0,2,1},{-1,-2,0,2,1}};
	
	filteredFile.open(vdiffName3, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == grayscalePixels.size()-1 || j == 0 || j == WIDTH - 1)
			{
				filteredFile << *(grayscalePixels[i] + j);
				vdiffArray[j] = *(grayscalePixels[i] + j);
			}
			else
			{
				for(k = -1; k < 2; k++)
				{
					for(m = -1; m < 2; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * hfilter3[k+1][m+1];
					}
				}
				pixelAverage = (255 + pixelSum/3)/2;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
				vdiffArray[j] = (unsigned char) pixelAverage;
			}
		}
		
		vdiffPixels3.push_back(vdiffArray);
		vdiffArray = new unsigned char[WIDTH];
	}

	filteredFile.close();

	filteredFile.open(vdiffName5, ios::binary);

	for(i = 0; i < grayscalePixels.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(i == 0 || i == 1 || i == grayscalePixels.size()-2 || i == grayscalePixels.size()-1 || j == 0 || j == 1 || j == WIDTH - 2 || j == WIDTH - 1)
			{
				filteredFile << *(grayscalePixels[i] + j);
				vdiffArray[j] = *(grayscalePixels[i] + j);
			}
			else
			{
				for(k = -2; k < 3; k++)
				{
					for(m = -2; m < 3; m ++)
					{
						pixelSum +=  (*(grayscalePixels[i + k] + j + m)) * hfilter5[k+2][m+2];
					}
				}
				pixelAverage = (255 + pixelSum/15)/2;
				pixelSum = 0;
				filteredFile << (unsigned char) pixelAverage;
				vdiffArray[j] = (unsigned char) pixelAverage;
			}
		}

		vdiffPixels5.push_back(vdiffArray);
		vdiffArray = new unsigned char[WIDTH];
	}

	filteredFile.close();
}


//*********************************************************** FUNCTION gradient *********************************************************//
/*This function will iterate through the vectors containing both the 3x3 horizontal and vertical differentiations and the 5x5 horizontral
and vertical differentiations. It will then calculate the gradient of these values and output the result to both a file and another 
unsigned character pixel vector for future use*/
/*******************************************************************************************************************************************/

void gradient(void)
{
	int i = 0;
	int j = 0;
	float hsquare = 0;
	float vsquare = 0;
	float grad = 0;
	unsigned char* gradArray = new unsigned char[WIDTH];


	//ensure that horizontal and vertical differential images have been created
	originalFile.open(hdiffName3);
	if(!originalFile.is_open())
	{
		originalFile.close();
		horizontalDifferentiate();
		verticalDifferentiate();
	}
	originalFile.close();
	

	filteredFile.open(gradientName3, ios::binary);

	cout << hdiffPixels3.size() << endl << vdiffPixels3.size() << endl << grayscalePixels.size() << endl;

	for(i = 0; i < hdiffPixels3.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(j == WIDTH - 1)
			{
				filteredFile << *(hdiffPixels3[i] + j);
				gradArray[j] = *(hdiffPixels3[i] + j);
			}
			else
			{
				hsquare = (*(hdiffPixels3[i] + j))*(*(hdiffPixels3[i] + j));
				vsquare = (*(vdiffPixels3[i] + j))*(*(vdiffPixels3[i] + j));
				grad = sqrt(hsquare+vsquare)*atan((float)(*(vdiffPixels3[i] + j))/(*(hdiffPixels3[i] + j)));
				filteredFile << (unsigned char) grad;
				gradArray[j] = (unsigned char) grad;
			}
			
		}
		
		gradientPixels3.push_back(gradArray);
		gradArray = new unsigned char[WIDTH];
	}

	filteredFile.close();

	filteredFile.open(gradientName5, ios::binary);

	gradArray = new unsigned char[WIDTH];

	for(i = 0; i < hdiffPixels5.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(j == WIDTH - 1)
			{
				filteredFile << *(hdiffPixels5[i] + j);
				gradArray[j] = *(hdiffPixels5[i] + j);
			}
			else
			{
				hsquare = (*(hdiffPixels5[i] + j))*(*(hdiffPixels5[i] + j));
				vsquare = (*(vdiffPixels5[i] + j))*(*(vdiffPixels5[i] + j));
				grad = sqrt(hsquare+vsquare)*atan((float)(*(vdiffPixels5[i] + j))/(*(hdiffPixels5[i] + j)));
				filteredFile << (unsigned char) grad;
				gradArray[j] = (unsigned char) grad;
			}
		}

		gradientPixels5.push_back(gradArray);
		gradArray = new unsigned char[WIDTH];
	}

	filteredFile.close();
}


//*********************************************************** FUNCTION threshold *********************************************************//
/*This function will prompt the user to enter an upper and lower threshold for pixel values [0,255] with range checking. It will then 
iterate through the vectors containg the results of both the 3x3 and 5x5 gradient calculations and if the pixel values are within the
user defined threshold limits, then it will output white to a file, else it will output black to a file.*/
/*******************************************************************************************************************************************/

void threshold(void)
{
	int i = 0;
	int j = 0;
	unsigned int input = -1;
	unsigned char upperThresh;
	unsigned char lowerThresh;

	originalFile.open(gradientName3);
	if(!originalFile.is_open())
	{
		originalFile.close();
		gradient();
	}
	originalFile.close();


	cout << gradientPixels3.size() << endl;
	while(input < 0 || input > 255)
	{
		cout << "Enter the upper threshold [0-255] " << endl;
		cin >> input;
	}
	upperThresh = (unsigned char) input;
	
	input = -1;
	while(input < 0 || input > 255)
	{
		cout << "Enter the lower threshold [0-255] " << endl;
		cin >> input;
	}
	lowerThresh = (unsigned char) input;

	filteredFile.open(threshName3, ios::binary);

	for(i = 0; i < gradientPixels3.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(j == WIDTH - 1)
				filteredFile << *(gradientPixels3[i] + j);
			else if(*(gradientPixels3[i] + j) >= upperThresh || *(gradientPixels3[i] + j) <= lowerThresh)
				filteredFile << (unsigned char) 0;
			else
				filteredFile << (unsigned char) 255;
		}
	}

	filteredFile.close();

	filteredFile.open(threshName5, ios::binary);

	for(i = 0; i < gradientPixels5.size(); i++)
	{
		for(j = 0; j < WIDTH; j++)
		{
			if(j == WIDTH - 1)
				filteredFile << *(gradientPixels5[i] + j);
			else if(*(gradientPixels5[i] + j) >= upperThresh || *(gradientPixels5[i] + j) <= lowerThresh)
				filteredFile << (unsigned char) 255;
			else
				filteredFile << (unsigned char) 0;
		}
	}

	filteredFile.close();
}