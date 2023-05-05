package test_instances;

//@CWRunner Class
//Solely used for SelectionHyperHeuristic class 
//CWRunner class object will be passed into singlePointSearch() function
//All the settings for the learning process, parameterize values will be stored
//within this class object and used in singlePointSearch() function
public class CWRunner {
	
	private int m_trials;
	private int m_steps;
	private int m_runTime;
	private int m_numOfSubsetsRemoved;
	private String m_filePath;
	private String m_fileName;
	private Double m_IOM;
	private Double m_DOS;
	private Double m_Pc;
	private Double m_T;
	
	//Constructor for CWRunner object
	CWRunner(int trials, int steps, int runTime, int numOfSubsetsRemoved, String filePath, String fileName, Double IOM, Double DOS, Double Pc, Double T){
		this.m_trials = trials;
	    this.m_steps = steps;
	    this.m_runTime = runTime;
	    this.m_numOfSubsetsRemoved = numOfSubsetsRemoved;
	    this.m_filePath = filePath;
	    this.m_fileName = fileName;
	    this.m_IOM = IOM;
	    this.m_DOS = DOS;
	    this.m_Pc = Pc;
	    this.m_T = T;
	}
	
	
	//Since all the class member variables are private, getter and setter functions
	//will be needed to modify all the variables after CWRunner object has been created.
	public int getTrials() {
		return m_trials;
	}

	public void setTrials(int trials) {
		this.m_trials = trials;
	}

	public int getSteps() {
		return m_steps;
	}

	public void setSteps(int steps) {
		this.m_steps = steps;
	}

	public int getRunTime() {
		return m_runTime;
	}

	public void setRunTime(int runTime) {
		this.m_runTime = runTime;
	}

	public int getNumOfSubsetsRemoved() {
		return m_numOfSubsetsRemoved;
	}

	public void setNumOfSubsetsRemoved(int numOfSubsetsRemoved) {
		this.m_numOfSubsetsRemoved = numOfSubsetsRemoved;
	}

	public String getFilePath() {
		return m_filePath;
	}

	public void setFilePath(String filePath) {
		this.m_filePath = filePath;
	}

	public String getFileName() {
		return m_fileName;
	}

	public void setFileName(String fileName) {
		this.m_fileName = fileName;
	}

	public Double getIOM() {
		return m_IOM;
	}

	public void setIOM(Double IOM) {
		this.m_IOM = IOM;
	}

	public Double getDOS() {
		return m_DOS;
	}

	public void setDOS(Double DOS) {
		this.m_DOS = DOS;
	}

	public Double getPc() {
		return m_Pc;
	}

	public void setPc(Double Pc) {
		this.m_Pc = Pc;
	}

	public Double getT() {
		return m_T;
	}

	public void setT(Double T) {
		this.m_T = T;
	}
}
