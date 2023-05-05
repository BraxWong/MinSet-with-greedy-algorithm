package test_instances;
import java.io.FileWriter;
import java.io.IOException;
//@OutputReader class
//Used to display the output in the correct format
public class OutputReader {

	//@displayBestSolution
	//@Param: int maxNumberOf, int trialNum, int objValue, int[] binarySolution
	//@Description: Displays the current trial and the best objValue that trial.
	//				Then prints out the binarySolution that represents that objValue.
	public void displayBestSolution(int maxNumberOf, int trialNum, int objValue, int[] binarySolution) {
		
		System.out.println("Trial#" + trialNum + ":\n" + objValue);
		for(int i = 0; i < binarySolution.length; ++i) {
			System.out.print(binarySolution[i]);
		}
		System.out.println();
		
	}
	
	//@bestNCurrentObjValueToFile
	//@Param: int best, int current, int trialNum, String filePath, String fileName, FileWriter writer
	//@Description: Writing the best and current objVal to the file using FileWriter object, which is passed in
	//				as an argument.
	//@Return: int objVal
	public int bestNCurrentObjValueToFile(int best, int current, int trialNum, String filePath, String fileName, FileWriter writer) throws IOException {
		
		if(current < best) {
			writer.write("Best-" + current + " current-" + current + "\n");
			return current;
		}
		writer.write("Best-" + best + " current-" + current + "\n");
		return best;
	}
	
	//@getObjValue
	//@Param: int[] binaryArray
	//@Description: Return the objVal
	//@Return: int objVal
	public int getObjValue(int[] binaryArray) {
		int objVal = 0;
		for(int i = 0; i < binaryArray.length; ++i) {
			if(binaryArray[i] == 1) {
				objVal++;
			}
		}
		return objVal;
	}
	

}
