package test_instances;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

class deltaSolution {
	int m_delta;
	int[] m_binaryArray;
	
	deltaSolution (int d, int[] BA){
		this.m_delta = d;
		m_binaryArray = new int[BA.length];
		m_binaryArray = Arrays.copyOf(BA, BA.length);
	}
}

//@Heuristic class
//Used to implement heuristics, which will be used for the SelectionHyperHeuristic class
public class Heuristics {

	//@intArraytoBoolArray
	//@Param: int[] binaryArray
	//@Description: This function will check whether the current index element of binaryArray is a 1 or 0
	//				If binaryArray[i] == 1 -> covered[i] ==  true || If binaryArray[i] == 0 -> covered[i] == false
	//@Return: Boolean[] covered after it has been populated accordingly
	private Boolean[] intArraytoBoolArray(int[] binaryArray) {
		Boolean[] covered = new Boolean[binaryArray.length];
		for(int i = 0; i < binaryArray.length; ++i) {
			if(binaryArray[i] == 1) {
				covered[i] = true;
			}
			else {
				covered[i] = false;
			}
		}
		return covered;
	}
	
	//@deltaEvaluation
	//@Param: int[] binaryArray, Vector<Vector<Integer>> v, boolean[] covered
	//@Description: This function will check if the current index is covered within the Boolean array
	//				If it is not, the delta variable will increment by 1. Else, it will decrement by 1.
	//@Return: int delta
	public int deltaEvaluation (int[] binaryArray, Vector<Vector<Integer>> v, Boolean[] covered) {
		int delta = 0;
		for(int i = 0; i < v.size(); ++i) {
			Vector<Integer> v2 = v.get(i);
			if(binaryArray[i] == 1 && !covered[i]) {
				for(int j = 0; j < v2.size(); ++j) {
					if(!covered[j]) {
						delta++;
					}
					covered[j] = true;
				}
			}
			else if(binaryArray[i] == 0 && covered[i]) {
				for(int j = 0; j < v2.size(); ++j) {
					if(covered[j]) {
						delta--;
					}
					covered[j] = false;
				}
			}
		}
		return delta;
	}
	
	//@bitFlip
	//@Param: int[] p, int index
	//@Description: This function will flip the p[index] value.
	//				If p[index] = 0 -> p[index] = 1 || p[index] = 1 -> p[index] = 0
	//@Return: The array p after the index has been flipped
	public int[] bitFlip(int[] p, int index) {
		p[index] = p[index] == 0 ? 1 : 0; 
		return p;
	}
	
	
	//@exchangeOperator
	//@Param: int[] p, String filePath
	//@Description: This function will exchange elements between 2 indexes of the array
	//				p. 2 random integers will be generated using random().nextInt() function.
	//				If these two numbers are the same or the elements in those indexes have the same 
	//				value. It will keep generating until these conditions are not satisfied.
	//				After the exchange, it will use validateSolution to check if the solution
	//				is still feasible. If so, return the p array.
	//@Return: The array p after the indexes are exchanged
	public int[] exchangeOperator(int[] p, String filePath) throws IOException{
		Random random = new Random();
		int[] backUp = p;
		int randomIndex1 = 0, randomIndex2 = 0;
		while(true) {
			//Flips 1 random bit and making sure they are not the same index
			while(true) {
				randomIndex1 = random.nextInt(p.length);
				randomIndex2 = random.nextInt(p.length);
				if(randomIndex2 != randomIndex1 && p[randomIndex2] != p[randomIndex1]) {
					break;
				}
			}
			
			int tmp = p[randomIndex1];
			p[randomIndex1] = p[randomIndex2];
			p[randomIndex2] = tmp;
			//Validate the solution
			if(validateSolution(p, filePath)) {

				break;
				
			}
			else {
				p = backUp;
			}
		}
		return p;
	}
	
	//@validateSolution
	//@Param: int[] p, String filePath
	//@Description: This function will check whether the binary array p is representing
	//				a feasible solution
	//@Return: true -> if p[] is feasible || false -> if p[] isn't feasible
	public Boolean validateSolution(int[] p, String filePath) throws IOException{
		InstanceReader ir = new InstanceReader();
		Vector<Vector<Integer>> v = ir.instanceReader(filePath);
		Vector<Integer> tmp = new Vector<Integer>();
		for(int i = 0; i < v.size(); ++i) {
			if(p[i] == 1) {
				Vector<Integer> v2 = v.get(i);
				for(int j = 0; j < v2.size(); ++j) {
					if(!tmp.contains(v2.get(j))) {
						tmp.add(v2.get(j));
					}
				}
			}
		}
		if(new SolutionInitialization().checkSolution(tmp, ir.getNumOfElements(filePath))) {
			return true;
		}
		return false;
	}

	//@validateSolution2
	//@Param: Vector<Integer> v, String filePath
	//@Description: This function will check whether the binary vector v is representing
	//				a feasible solution
	//@Return: true -> if v is feasible || false -> if v isn't feasible
	public Boolean validateSolution2(Vector<Integer> v, String filePath) throws IOException{
		InstanceReader ir = new InstanceReader();
		Vector<Vector<Integer>> v2 = ir.instanceReader(filePath);
		Vector<Integer> tmp = new Vector<Integer>();
		for(int i = 0; i < v2.size(); ++i) {
			if(v.get(i) == 1) {
				Vector<Integer> v3 = v2.get(i);
				for(int j = 0; j < v3.size(); ++j) {
					if(!tmp.contains(v3.get(j))) {
						tmp.add(v3.get(j));
					}
				}
			}
		}
		if(new SolutionInitialization().checkSolution(tmp, ir.getNumOfElements(filePath))) {
			return true;
		}
		return false;
	}
	
	

	
	//@ruinAndRecreate
	//@Param: int[] binaryArray, String filePath, int numOfSubsetsRemoved, double IOM
	//@Description: This function will remove a few subsets from being used as a potential candidate solution
	//				Then it will generate a new feasible solution. The solution will then be passed into
	//				DavisHillClimbing() to further improve the solution.
	//@Return: newSolution after it has gone through DavisHillClimbing()
	public int[] ruinAndRecreate(int[] binaryArray, String filePath, int numOfSubsetsRemoved, double IOM) throws IOException, InterruptedException {
		
		//ERROR
		if(numOfSubsetsRemoved <= 0 || numOfSubsetsRemoved >= binaryArray.length) {
			System.out.println("Error: The number of subsers removed can't be 0, equal or greater than the size of the binaryArray");
			return null;
		}
		
		
		InstanceReader ir = new InstanceReader();
		SolutionInitialization gh = new SolutionInitialization();
		BinaryEncoder be = new BinaryEncoder();
		Vector<Integer> removedSubset = new Vector<Integer>();

		//Step 1: Selects a number of subset to be removed from the current solution
		int[] newSolution = new int[binaryArray.length];
		while(numOfSubsetsRemoved != 0) {
			int randomNum = new Random().nextInt(binaryArray.length);
			if(binaryArray[randomNum] == 1 && !removedSubset.contains(randomNum)) {
				removedSubset.add(randomNum);
				numOfSubsetsRemoved--;
			}
		}
		
		//Step 2: Create a new solution
		while(true) {
			Boolean feasibleSolution = true;
			newSolution = gh.randomInit(ir.instanceReader(filePath), ir.getNumOfElements(filePath));
			//Making sure the solution does not include any removedSubset 
			for(int i = 0; i < removedSubset.size(); ++i) {
				if(newSolution[removedSubset.get(i)] == 1) {
					feasibleSolution = false;
				}
			}
			if(feasibleSolution) {
				break;
			}
		}
		
		//Step 3: Improvement using Davis' Hill Climbing Heuristic
		int[] backUp = Arrays.copyOf(newSolution,newSolution.length);
		newSolution = DavisHillClimbing(newSolution,filePath);
		
		//Accept if it is better, else return the original
		if(be.getObjValueArray(backUp) < be.getObjValueArray(newSolution)) {
			return backUp;
		}
		return newSolution;
	}
	
	//@steepestDescent
	//@Param: int[] binaryArray, String filePath
	//@Description: This function will attempt to bitFlip every single index of the binaryArray
	//				starting from index 0 -> index binaryArray.length - 1 (in this order).
	//				If that results in a better objective function value, it will be stored
	//				into backUp. If not, that bit will be reversed. This function will terminate
	//				after x amount of seconds.
	//@Return: backUp integer array
	public int[] steepestDescent(int[] binaryArray, String filePath) throws IOException{
		LocalDateTime startDateTime = LocalDateTime.now();
		BinaryEncoder be = new BinaryEncoder();
		int MaxIteration = 1000; int iteration = 0;

		Boolean improved = false;
		int[] backUp = binaryArray;
		int bestEval = be.getObjValueArray(backUp);
		System.out.println("Before Applying Steep: " + bestEval);
		while(iteration < MaxIteration) {
			if(Duration.between(startDateTime, LocalDateTime.now()).toSeconds() > 5) {
				break;
			}
			improved = false;
			for(int j=0; j< binaryArray.length; j++){ 
				//Flips the bit in index j of the binaryArray
				binaryArray = bitFlip(binaryArray,j);
				//Evaluate the binaryArray
				int tmpEval = be.getObjValueArray(binaryArray);
				//If it is better, store the tmpEval into bestEval and store binaryArray into backUp
				if(tmpEval < bestEval && validateSolution(binaryArray, filePath)) {
					bestEval = tmpEval;
					backUp = Arrays.copyOf(binaryArray, binaryArray.length);
					improved = true;
				}
				else {
					binaryArray = bitFlip(binaryArray,j);
				}
			}
			iteration++;
			
//			If no improvement, reset binaryArray to a new random initial solution
	        binaryArray = Arrays.copyOf(backUp,backUp.length);
		}
		System.out.println("After Applying Steep: " + be.getObjValueArray(backUp) + "\n Validity: " + validateSolution(backUp,filePath));
		return backUp;
		
	}
	
	//@DavisHillClimbing
	//@Param: int[] binaryArray, String filePath
	//@Description: First an integer array perm will be created with the size of binaryArray
	//				Then it will be populated with 0 -> binaryArray.length.
	//				Then the array will be shuffled using Collections.shuffle()
	//				Then the function will grab an element from perm and use that value
	//				to flip binaryArray. For example, if perm[0] = 20, then binaryArray[20]'s bit
	//				will be flipped.
	//				If that results in a better objective function value, it will be stored
	//				into backUp. If not, that bit will be reversed. This function will terminate
	//				after x amount of seconds.
	//@Return: backUp integer array
	public int[] DavisHillClimbing(int[] binaryArray, String filePath) throws IOException {
		LocalDateTime startDateTime = LocalDateTime.now();
		BinaryEncoder be = new BinaryEncoder();
		int[] perm = new int[binaryArray.length];
		int Max = 1000; int iteration = 0;
		for(int i = 0; i < perm.length; ++i) {
			perm[i] = i;
		}
		Collections.shuffle(Arrays.asList(perm));
		int backUp[] = Arrays.copyOf(binaryArray, binaryArray.length);
		int bestEval = be.getObjValueArray(binaryArray);
		System.out.println("Before Applying Davis: " + bestEval);
		while(iteration < Max) {
			if(Duration.between(startDateTime, LocalDateTime.now()).toSeconds() > 5) {
				break;
			}
			Boolean improved = false;
			for(int j = 0; j < perm.length; ++j) {
				int[] neighbor = bitFlip(binaryArray, perm[j]);
				int tmpEval = be.getObjValueArray(neighbor);
				if(tmpEval < bestEval && validateSolution(neighbor,filePath)) {
					bestEval = tmpEval;
					backUp = Arrays.copyOf(neighbor, neighbor.length);
					improved = true;
				}
				
			}
			binaryArray = Arrays.copyOf(backUp, backUp.length);
			++iteration;
		}
		System.out.println("After Applying Davis: " + be.getObjValueArray(backUp) + "\n Validity: " + validateSolution(backUp,filePath));
		return backUp;
	}

	//@uniformCrossover
	//@Param: int[] parentOne, int[] parentTwo, String filePath
	//@Description: In each iteration, if random.nextDouble(1) is greater than 0.5
	//				childOne will get an element from parentTwo. And childTwo will get an element 
	//				from parentOne. An extra layer of mutation can also be trigger if random.nextDouble(1) 
	//				is greater than 0.2, a bitFlip operator will be used.
	//				After that, both childOne and childTwo will be passed into validateSolution2() to 
	//				check whether they are feasible solutions.
	//@Return: Both childOne and childTwo stored into Vector<Vector<Integer>> binaryArray
	public Vector<Vector<Integer>> uniformCrossover(int[] parentOne, int[] parentTwo, String filePath) throws IOException{
		Vector<Vector<Integer>> binaryArrays = new Vector<Vector<Integer>>();
		Random random = new Random();
		int maxIteration = 1000, iteration = 0;
		while(iteration < maxIteration) {
			Vector<Integer> childOne = new Vector<Integer>();
			Vector<Integer> childTwo = new Vector<Integer>();
			for(int i = 0; i < parentOne.length; ++i) {
				//Switching
				if(random.nextDouble(1) > 0.5) {
					//Mutation
					if(random.nextDouble(1) > 0.2) {
						parentOne[i] = parentOne[i] == 0 ? 1 : 0;
						parentTwo[i] = parentTwo[i] == 0 ? 1 : 0;
					}	
					childOne.add(parentTwo[i]);
					childTwo.add(parentOne[i]);
				}
				//Remaining the same
				else {
					//Mutation
					if(random.nextDouble(1) > 0.2) {
						parentOne[i] = parentOne[i] == 0 ? 1 : 0;
						parentTwo[i] = parentTwo[i] == 0 ? 1 : 0;
					}	
					childOne.add(parentOne[i]);
					childTwo.add(parentTwo[i]);
				}
			}
			//Found the solution, exiting function
			if(validateSolution2(childOne,filePath) && validateSolution2(childTwo,filePath)) {
				binaryArrays.add(childOne);
				binaryArrays.add(childTwo);
				return binaryArrays;
			}
			iteration++;
		}
		return binaryArrays;
	}
	
	//@onePtCrossover
	//@Param: int[] parentOne, int[] parentTwo, double Pc, String filePath
	//@Description: A random number will be generated using random.nextInt(). This will act as the
	//				crossover site. If that random number is 25, then childOne will get the elements 
	//				of ParentOne from 0 -> 25 and ParentTwo's elements from 26-> parentTwo.length. 
	//				The same will be done for childTwo as well. An extra layer of mutation has been implemented
	//				as well. If random.nextDouble(1) > 0.2, a bitFlip operator will be used.
	//				After that, both childOne and childTwo will be passed into validateSolution2() to 
	//				check whether they are feasible solutions.
	//@Return: Both childOne and childTwo stored into Vector<Vector<Integer>> binaryArray
	public Vector<Vector<Integer>> onePtCrossover(int[] parentOne, int[] parentTwo, double Pc, String filePath) throws IOException {
		Random random = new Random();
		double randomNum = random.nextDouble(1);
		System.out.println("Random Number is: " + randomNum);
		Vector<Integer> childOne = new Vector<Integer>();
		Vector<Integer> childTwo = new Vector<Integer>();
		Vector<Vector<Integer>> binaryArrays = new Vector<Vector<Integer>>();
		if(randomNum < Pc) {
			int maxIterations = 1000, iteration = 0;
			while(iteration < maxIterations) {
				int crossoverSite = random.nextInt(parentOne.length);
				childOne = new Vector<Integer>();
				childTwo = new Vector<Integer>();
				for(int i = 0; i < crossoverSite; ++i) {
					//Mutation
					if(random.nextDouble(1) > 0.2) {
						parentOne[i] = parentOne[i] == 0 ? 1 : 0;
						parentTwo[i] = parentTwo[i] == 0 ? 1 : 0;
					}	
					childOne.add(parentOne[i]);
					childTwo.add(parentTwo[i]);	
				}
				for(int i = crossoverSite; i < parentOne.length; ++i) {
					//Mutation
					if(random.nextDouble(1) > 0.2) {
						parentOne[i] = parentOne[i] == 0 ? 1 : 0;
						parentTwo[i] = parentTwo[i] == 0 ? 1 : 0;
					}	
					childOne.add(parentTwo[i]);
					childTwo.add(parentOne[i]);
				}
				
				if(validateSolution2(childOne,filePath) && validateSolution2(childTwo,filePath)) {
					binaryArrays.add(childOne);
					binaryArrays.add(childTwo);
					return binaryArrays;
				}
				iteration++;
			}
		}
		return binaryArrays;
	}
}

