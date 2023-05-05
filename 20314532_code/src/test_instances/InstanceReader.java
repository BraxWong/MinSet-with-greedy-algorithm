package test_instances;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

//@VectorIndex
//Used to store vector which is a subset and its index.
//Want to retain its index since the vector of a vector will be shuffled
class VectorIndex {
	int index;
	Vector<Integer> vector;
	public VectorIndex(int i, Vector<Integer> v) {
		this.index = i;
		this.vector = v;
	}
}

//@InstanceReader class
//Used to parse test instnace files into operatable data
public class InstanceReader {

	//@instanceReader
	//@Param: String path
	//@Description: It will turn a test instance text file into a Vector<Vector<Integer>> 
	//				so it can be used afterwards.
	//@Return: Vector<Vector<Integer>> result;
	public Vector<Vector<Integer>> instanceReader(String path) throws IOException {
		FileReader fileReader = new FileReader(path);
		int iteration = 0, sizeofSubset = 0;
		Vector<Integer> newVector = new Vector<Integer>();
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		try (BufferedReader br = new BufferedReader(fileReader)) {
            String line;
            
            while ((line = br.readLine()) != null) {
            	//Adding null value to the vector to indicate the end of a subset
            	if(iteration > 2 && sizeofSubset == 0) {
            		result.addElement(newVector);
            		newVector = new Vector<Integer>();
            	}
            	
            	//Avoid reading the first line
            	if(iteration != 0) {
            		String[] words = line.trim().split("\\s+");
            		//Get the size of the current subset. 
            		//There are lines where there is only 1 element in a line and that is not the sizeofSubset
            		if(words.length == 1 && sizeofSubset == 0) {
            			sizeofSubset = Integer.parseInt(words[0]);
            		}
            		else {
            			for(int i = 0; i < words.length; ++i) {
            				newVector.add(Integer.parseInt(words[i]));
            				sizeofSubset--;
            			}
            			
            		}
            	}
            	
            	iteration++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		//Adding the last vector to the Vector<Vector<Integer>>
	    if (!newVector.isEmpty()) {
	        result.addElement(newVector);
	    }
		return result;
		
	}
	
	//@getNumOfSubset
	//@Param: String path
	//@Description: return the number of subsets after parsing the test instance
	//				file.
	//@Return: Number of subsets
	public int getNumOfSubsets(String path) throws IOException {
		String line;
		FileReader fileReader = new FileReader(path);
		try (BufferedReader br = new BufferedReader(fileReader)){
			while((line = br.readLine())!= null) {
				String[] words = line.trim().split("\\s+");
				return Integer.parseInt(words[0]);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	//@getNumOfElements
	//@Param: String path
	//@Description: return the number of elements after parsing the test instance
	//				file.
	//@Return: int Number of elements
	public int getNumOfElements(String path) throws IOException {
		String line;
		FileReader fileReader = new FileReader(path);
		try (BufferedReader br = new BufferedReader(fileReader)){
			while((line = br.readLine())!= null) {
				String[] words = line.trim().split("\\s+");
				return Integer.parseInt(words[1]);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	//@getProblemInstance
	//@Param: String fileName
	//@Description: return the problem instance, which is the first 2 characters
	//				of the test instance file name.
	//@Return: String problemInstance
	public String getProblemInstance(String fileName) {
		String[] words = fileName.trim().split("_");
		return words[0];
	}
	
	//@sortVector
	//@Param: Vector<VectorIndex> vectorIndexList
	//@Description: Vector<VectorIndex> vectorIndexList will be sorted based on the 
	//				vector size.
	//@Return: Vector<VectorIndex> vectorIndexList after it has been sorted
	public Vector<VectorIndex> sortVector(Vector<VectorIndex> vectorIndexList){
	     Collections.sort(vectorIndexList, new Comparator<VectorIndex>() {
	            public int compare(VectorIndex v1, VectorIndex v2) {
	                return Integer.compare(v2.vector.size(), v1.vector.size());
	            }
	        });
	     return vectorIndexList;
	}
	
}
