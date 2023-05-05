package test_instances;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

//@MoveAcceptance class
//Used to implement simulateAnnealing 
public class MoveAcceptance {

	//@BoltzmannProbability
	//@Param: double delta, double temp
	//@Description: Calculates the boltzmann probability then return it
	//@Return: double boltzmannProbability
	private double BoltzmannProbability(double delta, double temp) {
		return Math.exp(-delta/temp);
	}
	
	//@lundyMees
	//@Param: double T, double B
	//@Description: Recalibrate the temperature using lundy Mees formula
	//@Return: double temp
	private double lundyMees(double T, double B) {
		return T/(1+B*T);
	}

	//@simulatedAnnealing
	//@Param: double T0, double Tfinal, int[] binaryArray, String filePath
	//@Description: Applying simulatedAnnealing to binaryArray
	//@Return: int[] binaryArray
	public int[] simulatedAnnealing(double T0, double Tfinal, int[] binaryArray, String filePath) throws IOException {
		//The initial temperature can't be LEQ to 0. || The initial temperature and the final temperature can't be the same as well
		if(T0 <= 0 | T0 == Tfinal) {
			return null;
		}
		Heuristics h = new Heuristics();
		BinaryEncoder be = new BinaryEncoder();
		Random random = new Random();
		long startTime = System.currentTimeMillis();
		double T = T0;
		int[] sBest = Arrays.copyOf(binaryArray, binaryArray.length); int[] s = Arrays.copyOf(binaryArray, binaryArray.length);
		while(System.currentTimeMillis() - startTime < 20 * 1000) {
			System.out.print("Here");
			int[]sP = h.exchangeOperator(s, filePath);
			int delta = be.getObjValueArray(sP) - be.getObjValueArray(s);
			double r = random.nextDouble(1);
			if(delta < 0 | r < BoltzmannProbability(delta,T)) {
				s = Arrays.copyOf(sP, sP.length);
			}
			if(be.getObjValueArray(s) < be.getObjValueArray(sBest)) {
				sBest = Arrays.copyOf(s, s.length);
			}
			T = lundyMees(T,0.005);
		}
		return sBest;
	}


}
