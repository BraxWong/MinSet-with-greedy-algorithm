#AI CW README



#MinSet-with-greedy-algorithm

This is a java oriented project that allows users to customize their own test instances (Problem)
and solve the test instances using greedy algorthm alongside with other low-level heuristics such as
hillclimbing, bitflip, and more. For more information please check out the documentation provided in 
each class.

## Installation
Installation is not needed as everything is provided. All that is left is importing the src folder
into an IDE of your choice and everything will be ready to go.

## Usage

In order to run the code, please head over to SelectionHyperHeuristic class. In that file, you will find a main function which allows you to run all sorts of tests.
All the file paths and file names of the test instances have been stored as an array. A CWRunner object has been created as well, which takes int trials, int steps, int runTime, int numOfSubsetsRemoved, String filePath, String fileName,
Double IOM, Double DOS, Double Pc, and Double T as arguments. Then you can call the singlePointSearch function, which will execute the entire program for you when the CWRunner object have been passed in.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

