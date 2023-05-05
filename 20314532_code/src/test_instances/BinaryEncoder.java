package test_instances;
import java.util.Vector;

//@BinaryEncoder Class
//Used to convert a boolean array of solution into an integer array of 0s and 1s
//And getting the objective values of an array or a vector data structure

public class BinaryEncoder {

	//@binaryEncoder
	//@Param: Boolean[] binaryArray
	//@Description: Takes a boolean array solution generated from GreedyHeuristic class.
	//				And convert it based on the value. True -> 1 || False -> 0
	//@Return: The integer array consists of 0s and 1s
	public int[] binaryEncoder(Boolean[] binaryArray2) {
		int[] binaryArray = new int[binaryArray2.length];
		for(int i = 0; i < binaryArray2.length; ++i) {
			if(binaryArray2[i]) {
				binaryArray[i] = 1;
			}
			else {
				binaryArray[i] = 0;
			}
		}
		return binaryArray;
	}
	
	//@getObjValueArray
	//@Param: int[] binaryArray
	//@Description: If the current index element of the binaryArray is a 1,
	//				the objValue variable will increment by 1. If it is a 0,
	//				the objValue will neither increment nor decrement.
	//@Return: The objValue after it has been processed
	public int getObjValueArray(int[] binaryArray) {
		int objValue = 0;
		for(int i = 0; i < binaryArray.length; ++i) {
			if(binaryArray[i] == 1) {
				objValue++;
			}
		}
		return objValue;
	}
	
	//@getObjValueVector
	//@Param: Vector<Integer> binaryArray
	//@Description: If the current index element of the binaryArray is a 1,
	//				the objValue variable will increment by 1. If it is a 0,
	//				the objValue will neither increment nor decrement.
	//@Return: The objValue after it has been processed
	public int getObjValueVector(Vector<Integer> binaryArray) {
		int res = 0;
		for(int i = 0; i < binaryArray.size(); ++i) {
			if(binaryArray.get(i) == 1) {
				res += 1;
			}
		}
		return res;
	}

}
