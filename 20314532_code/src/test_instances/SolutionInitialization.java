package test_instances;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
//FIXME: Use a different name instead of GreedyHeuristic
//@GreedyHeuristic class
//Used for candidate solution initialization and solution validation
public class SolutionInitialization {
	
	
	//@checkSolution
	//@Param: Vector<Integer> binarySolution, int numofElements
	//@Description: Checks if the binarySolution vector includes all the number up to 
	//				the numofElements variable. If numofElements = 10, binarySolution
	//				should have 1,2,3,4,5,6,7,8,9,10
	//@Return: true if binarySolution has all the elements, false otherwise.
	public Boolean checkSolution(Vector<Integer> binarySolution, int numofElements) {
	    for(int k = 1; k <= numofElements; ++k) {
	        if(!binarySolution.contains(k)) {
	            return false;
	        }
	    }
	    Collections.sort(binarySolution);
	    return true;
	}
	
	//@solutionInit
	//@Param: Vector<Vector<Integer> v, int numofElements
	//@Description: It is a constructive heuristic.
	//				The argument v is a vector of vector of integer consists of 
	//				all the data from the test instances file.
	//				Within the for loop, it will check if the vector tmp
	//				has the element from v, if it does not, it will mark 
	//				the current index within binaryArray as true.
	//				Then binaryEncoder() will be called to convert the boolean array
	//				into an integer array of 0s and 1s.
	//@Return: The candidate solution in the form of an integer array -> binaryArray
	public int[] solutionInit(Vector<Vector<Integer>> v, int numofElements) {
		Boolean[] binaryArray = new Boolean[v.size()];
		Arrays.fill(binaryArray, false);
		Vector<Integer> tmp = new Vector<Integer>();
		for(int i = 0; i < v.size(); ++i) {
			Vector<Integer> v3 = v.get(i);
			//If it is the first iteration, store the vector into tmp
			if(i == 0) {
				binaryArray[i] = true;
				tmp = v3;
			}
			else {
				for(int j = 0; j < v3.size(); ++j) {
					if(!tmp.contains(v3.get(j))) {
						tmp.add(v3.get(j));
					}
				}
				binaryArray[i] = true;
			}
			
			//Check if the array contains all the number
			if(checkSolution(tmp, numofElements)) {
	            return new BinaryEncoder().binaryEncoder(binaryArray);
	        }
		}
		
		return new BinaryEncoder().binaryEncoder(binaryArray);
	}
	
	
	//@solutionInit3
	//@Param: Vector<Vector<Intger>> v, int numofElements
	//@Description: This function works very similar to solutionInit.
	//				The only difference is VectorIndex is being used since
	//				the vector will be sorted based on the size of the subset.
	//              binaryEncoder() will be called to convert the boolean array
	//				into an integer array of 0s and 1s.
	//@Return: The candidate solution in the form of an integer array -> binaryArray
	public int[] solutionInit3(Vector<Vector<Integer>> v, int numofElements) {
	
		InstanceReader ir = new InstanceReader();
		BinaryEncoder be = new BinaryEncoder();
		Boolean[] binaryArray = new Boolean[v.size()];
		//Populate the entire binaryArray with false
		Arrays.fill(binaryArray, false);
		//tmp is for remember what we already have
		Vector<Integer> tmp = new Vector<Integer>();
		Vector<VectorIndex> v2 = new Vector<VectorIndex>();
		
		//Sorting the VectorIndex 
		for(int i = 0; i < v.size(); ++i) {
			v2.add(new VectorIndex(i,v.get(i)));
		}
		v2 = ir.sortVector(v2);
		
		
		for(int i = 0; i < v2.size(); ++i) {
			VectorIndex v3 = v2.get(i);
			//If it is the first iteration, store the vector into tmp
			if(i == 0) {
				binaryArray[v3.index] = true;
				tmp = v3.vector;
			}
			
			else {
				for(int j = 0; j < v3.vector.size(); ++j) {
					//Add it to the current elem in v3 into tmp if tmp doesn't have it yet
					if(!tmp.contains(v3.vector.get(j))) {
						tmp.add(v3.vector.get(j));
					}
				}
				//Set the current binaryArray index -> true
				binaryArray[v3.index] = true;
			}
			
			//Check if the array contains all the number
				if(checkSolution(tmp, numofElements)) {
		            return new BinaryEncoder().binaryEncoder(binaryArray);
		        }
			}
			
			return be.binaryEncoder(binaryArray);
	}
	
	
	//@randomInit
	//@Param: Vector<Vector<Intger>> v, int numofElements
	//@Description: This function works very similar to solutionInit2.
	//				The only difference is VectorIndex is being used and all the order
	//				of the subset will be shuffled. VectorIndex will be used to store
	//				the original index of the subset within the test instance file.
	//@Return: The candidate solution in the form of an integer array -> binaryArray
	public int[] randomInit(Vector<Vector<Integer>> v, int numofElements) {
		Boolean[] binaryArray = new Boolean[v.size()];
		Arrays.fill(binaryArray, false);
		//tmp is for remember what we already have
		Vector<Integer> tmp = new Vector<Integer>();
		Vector<VectorIndex> v2 = new Vector<VectorIndex>();
		
		//Shuffles the Vector of VectorIndex to create the randomness
		for(int i = 0; i < v.size(); ++i) {
			v2.add(new VectorIndex(i,v.get(i)));
		}
		Collections.shuffle(v2);
		
		for(int i = 0; i < v2.size(); ++i) {
			VectorIndex v3 = v2.get(i);
	
			//If it is the first iteration, store the vector into tmp
			if(i == 0) {
				binaryArray[v3.index] = true;
				tmp = v3.vector;
			}
			
			else {
				for(int j = 0; j < v3.vector.size(); ++j) {
					//Add it to the current elem in v3 into tmp if tmp doesn't have it yet
					if(!tmp.contains(v3.vector.get(j))) {
						tmp.add(v3.vector.get(j));
					}
				}
				//Set the current binaryArray index -> true
				binaryArray[v3.index] = true;
			}
			
			//Check if the array contains all the number
			if(checkSolution(tmp, numofElements)) {
	            return new BinaryEncoder().binaryEncoder(binaryArray);
	        }
		}
		return new BinaryEncoder().binaryEncoder(binaryArray);
	}
	
	//@randomInit2
	//@Param: Vector<Vector<Intger>> v, int numofElements
	//@Description: This function will very likely generate 
	//				an infeasible candidate solution
	//@Return: The candidate solution in the form of an integer array -> binaryArray
	public int[] randomInit2(Vector<Vector<Integer>> v, int numofElements) {
		int[] binaryArray = new int[v.size()];
		for(int i = 0; i < binaryArray.length; ++i) {
			binaryArray[i] = new Random().nextInt(2);
		}
		return binaryArray;
	}
	

}
