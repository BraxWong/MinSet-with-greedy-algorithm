package test_instances;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

//@solution
//Used to store an integer array which is a binaryArray and its objective function value
class solution {
	int[] binaryArray;
	int objFunctionValue;
	String heuristic;
	
	//Constructor
	solution(int[] arr, int obj, String h){
		this.binaryArray = Arrays.copyOf(arr, arr.length);
		this.objFunctionValue = obj;
		this.heuristic = h;
	}
	
	//@sortSolutionVector
	//@Param: Vector<solution>vec
	//@Description: This function will sort vec according to its objFunctionValue
	//@Return: Vector<solution>vec after it has been sorted
	public void sortSolutionVector(Vector<solution> vec) {
	    Comparator<solution> comparator = new Comparator<solution>() {
	        @Override
	        public int compare(solution s1, solution s2) {
	            return Integer.compare(s2.objFunctionValue, s1.objFunctionValue);
	        }
	    };
	    Collections.sort(vec, comparator);
	}
}

//@SelectionHyperHeuristic class
//Run the entire java project from here. Pretty much is the runner of the coursework
public class SelectionHyperHeuristic {
	
	//@vectorToArray
	//@Param: Vector<Integer> v
	//@Description: Convert a vector into an integer array
	//@Return: int[] binaryArray
	private static int[] vectorToArray(Vector<Integer> v) {
		int[] binaryArray = new int[v.size()];
		for(int i = 0; i < v.size(); ++i) {
			binaryArray[i] = v.get(i);
		}
		return binaryArray;
	}
	
	
	//@singlePointSearch
	//@Param: CWRunner cw
	//@Description: Applying the singlePointSearch. It will run for x amount of trials or x amount of minutes before it terminates.
	//				Will generate a initial candidate solution. Then it will be passed to multiple heuristic (bitFlip, exchangeOperator,
	//				SteepestDescent, DavisHillClimbing, UniformCrossover, OnePointCrossover). Then the best solution out of them will be 
	//				passed into simulated annealing to potential improve the solution. After certain amount of trials, the function will
	//				terminate and return the binarySolution with the best ObjFunctionVal.
	//@Return: int[] binaryArray
	public static int[] singlePointSearch(CWRunner cw) throws IOException, InterruptedException {
		LocalDateTime startDateTime = LocalDateTime.now();
		BinaryEncoder be = new BinaryEncoder();
		InstanceReader ir = new InstanceReader();
		SolutionInitialization gh = new SolutionInitialization();
		Heuristics h = new Heuristics();
		Parameterizer pz = new Parameterizer();
		MoveAcceptance ma = new MoveAcceptance();
		OutputReader or = new OutputReader();
		String fn = ir.getProblemInstance(cw.getFileName()) + '_' + 
				ir.getNumOfSubsets(cw.getFilePath()) + '_' + ir.getNumOfElements(cw.getFilePath()) + '_' + 1 + "_output.txt";
		
		//If the file exists already, it will cause trouble when writing data into it
		//Need to delete the file first.
		File file = new File(fn);
		if(file.exists()) {
			file.delete();
		}
		
		
		FileWriter fw = new FileWriter(fn,true);
		//Step 1 : Generate 1 solution
		int bestObjVal = Integer.MAX_VALUE;
		int trial = 0;
		int numOfSubsets = ir.getNumOfSubsets(cw.getFilePath()); 
		int numOfElements = ir.getNumOfElements(cw.getFilePath());
		int[] bitSolution = gh.randomInit(ir.instanceReader(cw.getFilePath()), numOfElements);
		int[] bitSolution2 = Arrays.copyOf(bitSolution, bitSolution.length); int[] bitSolution3 = Arrays.copyOf(bitSolution, bitSolution.length); int[] bitSolution4 =  Arrays.copyOf(bitSolution, bitSolution.length); int[] bitSolution7 = Arrays.copyOf(bitSolution, bitSolution.length);
		int[] s = new int[bitSolution3.length]; 
		solution s1 = new solution(bitSolution,be.getObjValueArray(bitSolution),"NULL");
		Random random = new Random();
		
		//Step 2 : While Termination Criteria Isn't Satisfied
		while(cw.getTrials() != 0 ) {
			if(Duration.between(startDateTime, LocalDateTime.now()).toMinutes() > cw.getRunTime()) {
				break;
			}
			Vector<solution> solution = new Vector<solution>();
			System.out.println("Current Trial Num: " + cw.getTrials());
			//Step 3 : Select a heuristic (Using Greedy for learning)
			//BitFlip, Exchange Operator, Ruin and Recreate Section
			for(int i = 0; i < pz.IOM(cw.getIOM()); ++i) {
				while(true) {
					System.out.println("Before bitFlip val: " + be.getObjValueArray(bitSolution));
					bitSolution = h.bitFlip(bitSolution, random.nextInt(numOfSubsets));
					//Only allowing feasible solution, might slow down the entire process.
					if(h.validateSolution(bitSolution,cw.getFilePath())){
						System.out.println("After bitFlip val: " + be.getObjValueArray(bitSolution));
						cw.setSteps(cw.getSteps()-1);
						bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(bitSolution), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
						break;
					}
				}
				System.out.println("Before ex val: " + be.getObjValueArray(bitSolution2));
				bitSolution2 = h.exchangeOperator(bitSolution2, cw.getFilePath());
				System.out.println("After ex val: " + be.getObjValueArray(bitSolution2));
				bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(bitSolution2), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
				cw.setSteps(cw.getSteps()-1);
				
				bitSolution7 = h.ruinAndRecreate(bitSolution7, cw.getFilePath(), cw.getNumOfSubsetsRemoved(), cw.getIOM());		
				bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(bitSolution7), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
				cw.setSteps(cw.getSteps()-1);
				
			}
			solution.add(new solution(bitSolution,be.getObjValueArray(bitSolution),"BitFlip"));
			solution.add(new solution(bitSolution2,be.getObjValueArray(bitSolution2),"Exchange Operator"));
			solution.add(new solution(bitSolution7,be.getObjValueArray(bitSolution7),"Ruin and Recreate"));
			//Steepest Descent and Davis's Hill Climbing Heuristics Section
			for(int j = 0; j < pz.DOS(cw.getDOS()); ++j) {
					bitSolution3 = h.steepestDescent(Arrays.copyOf(bitSolution3, bitSolution3.length), cw.getFilePath());
					cw.setSteps(cw.getSteps()-1);
					bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(bitSolution3), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
					System.out.println("Steep: " + "Best: " + bestObjVal + " Current: " + be.getObjValueArray(bitSolution3));
					
					bitSolution4 = h.DavisHillClimbing(Arrays.copyOf(bitSolution4, bitSolution4.length), cw.getFilePath());
					cw.setSteps(cw.getSteps()-1);
					bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(bitSolution4), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
					System.out.println("Davis: " + "Best: " + bestObjVal + " Current: " + be.getObjValueArray(bitSolution4));

			}
			solution.add(new solution(bitSolution3,be.getObjValueArray(bitSolution3),"Steepest Descent"));
			solution.add(new solution(bitSolution4,be.getObjValueArray(bitSolution4), "Davis Hill Climbing"));
			//One Point and Uniform Crossover Section
			Vector<Vector<Integer>> bitSolution5 = h.onePtCrossover(Arrays.copyOf(bitSolution, bitSolution.length), gh.randomInit(ir.instanceReader(cw.getFilePath()), numOfElements),cw.getPc(),cw.getFilePath());
			cw.setSteps(cw.getSteps()-2);
			bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueVector(bitSolution5.get(0)), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
			System.out.println("One Point Crossover(1): " + "Best: " + bestObjVal + " Current: " + be.getObjValueVector(bitSolution5.get(0)));
			bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueVector(bitSolution5.get(1)), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
			System.out.println("One Point Crossover(2): " + "Best: " + bestObjVal + " Current: " + be.getObjValueVector(bitSolution5.get(1)));
			Vector<Vector<Integer>> bitSolution6 = h.uniformCrossover(Arrays.copyOf(bitSolution, bitSolution.length), gh.randomInit(ir.instanceReader(cw.getFilePath()), numOfElements),cw.getFilePath());
			cw.setSteps(cw.getSteps()-2);
			bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueVector(bitSolution6.get(0)), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
			System.out.println("Uniform Crossover(1): " + "Best: " + bestObjVal + " Current: " + be.getObjValueVector(bitSolution6.get(0)));
			bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueVector(bitSolution6.get(1)), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
			System.out.println("Uniform Crossover(2): " + "Best: " + bestObjVal + " Current: " + be.getObjValueVector(bitSolution5.get(0)));
			
			
			
			solution.add(new solution(vectorToArray(bitSolution5.get(0)),be.getObjValueArray(vectorToArray(bitSolution5.get(0))),"One Point Crossover"));
			solution.add(new solution(vectorToArray(bitSolution5.get(1)),be.getObjValueArray(vectorToArray(bitSolution5.get(1))),"One Point Crossover"));
			solution.add(new solution(vectorToArray(bitSolution6.get(0)),be.getObjValueArray(vectorToArray(bitSolution6.get(0))),"Uniform Crossover"));
			solution.add(new solution(vectorToArray(bitSolution6.get(1)),be.getObjValueArray(vectorToArray(bitSolution6.get(1))),"Uniform Crossover"));

			//Sorting the solutions based on the objective function value
			s1.sortSolutionVector(solution);
			
			for(int i = 0; i < solution.size(); ++i) {
				System.out.println(solution.get(i).objFunctionValue);
			}
			
			//Step 5: Move Acceptance Method (Simulated Annealing)
			//Compare the current answer with the current best. If the current answer is better, copy the current answer to the current best answer
			if(trial != 0) {
				if(be.getObjValueArray(ma.simulatedAnnealing(cw.getT(), 0.0, solution.get(solution.size() - 1).binaryArray , cw.getFilePath())) < be.getObjValueArray(s)) {
					s = solution.get(solution.size() - 1).binaryArray;
					or.displayBestSolution(5, trial + 1, be.getObjValueArray(s), s);
					bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(s), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
					cw.setSteps(cw.getSteps()-2);
					System.out.println("Steps: " + cw.getSteps());
				}
				else {
					or.displayBestSolution(5, trial + 1, be.getObjValueArray(s), s);
					bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(solution.get(solution.size() - 1).binaryArray), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
					cw.setSteps(cw.getSteps()-2);
					System.out.println("Steps: " + cw.getSteps());
				}
				
			}
			else {
				System.out.println("Best: " + be.getObjValueArray(solution.get(solution.size()-1).binaryArray));
				s = ma.simulatedAnnealing(cw.getT(), 0.0, solution.get(solution.size() - 1).binaryArray, cw.getFilePath());
				System.out.println("After Annealing: Best: " + be.getObjValueArray(s));
				or.displayBestSolution(5, trial + 1, be.getObjValueArray(s), s);
				bestObjVal = or.bestNCurrentObjValueToFile(bestObjVal, be.getObjValueArray(s), trial + 1, cw.getFilePath(), cw.getFileName(), fw);
				cw.setSteps(cw.getSteps()-2);
				System.out.println("Steps: " + cw.getSteps());
			}
			trial++;
			cw.setTrials(cw.getTrials()-1);;
		}
			fw.close();
			return s;
	}
	
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		String[] testInstances = {"src/test_instances/d1_50_500.txt", "src/test_instances/d2_50_500.txt", "src/test_instances/d3_511_210.txt", "src/test_instances/d4_2047_495.txt"};
		String[] fileName = {"d1_50_500.txt","d2_50_500.txt","d3_511_210.txt","d4_2047_495.txt"};
		CWRunner cw = new CWRunner(5,100,2,5,testInstances[0],fileName[0],0.2,0.2,0.99,10.0);
		singlePointSearch(cw);

	}

}

