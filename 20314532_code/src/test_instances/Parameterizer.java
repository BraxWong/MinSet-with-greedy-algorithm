package test_instances;

//@Parameterizer class
//Used to determine the amount of times the operator has to be run
public class Parameterizer {

	//@IOM
	//@Param: double iom
	//@Description: Determine the iom 
	//@Return: int 
	public int IOM(double iom) {
		if(iom >= 0.0 && iom < 0.2) {
			return 1;
		}
		else if(iom >= 0.2 && iom < 0.4) {
			return 2;
		}
		else if(iom >= 0.4 && iom < 0.6) {
			return 3;
		}
		else if(iom >= 0.6 && iom < 0.8) {
			return 4;
		}
		else if(iom >= 0.8 && iom < 1.0) {
			return 5;
		}
		else if(iom == 1.0) {
			return 6;
		}
		else {
			return -1;
		}
	}
	
	//@DOS
	//@Param: double dos
	//@Description: Determine the dos
	//@Return: int 
	public int DOS(double dos) {
		if(dos >= 0.0 && dos < 0.2) {
			return 1;
		}
		else if(dos >= 0.2 && dos < 0.4) {
			return 2;
		}
		else if(dos >= 0.4 && dos < 0.6) {
			return 3;
		}
		else if(dos >= 0.6 && dos < 0.8) {
			return 4;
		}
		else if(dos >= 0.8 && dos < 1.0) {
			return 5;
		}
		else if(dos == 1.0) {
			return 6;
		}
		else {
			return -1;
		}
	}

}
