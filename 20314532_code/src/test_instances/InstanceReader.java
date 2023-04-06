package test_instances;
import java.io.*;

public class InstanceReader {

	

	public static void instanceReader(String path) throws IOException {
		FileReader fileReader = new FileReader(path);
		int numOfSubsets = 0, numOfElements = 0, iteration = 0;
		
		try (BufferedReader br = new BufferedReader(fileReader)) {
			
			//get the numOfSubsets and numOfElements
            String line;
            
            while ((line = br.readLine()) != null) {
            	if(iteration == 0) {
            		iteration++;
            		String[] words = line.trim().split("\\s+");
            		numOfSubsets = Integer.parseInt(words[0]);
            		numOfElements = Integer.parseInt(words[1]);
            	}
            	
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		System.out.println("Number of Subsets: " + numOfSubsets + "\nNumber of Elements: " + numOfElements);
	}

	public static void main(String[] args) throws IOException {
	
		instanceReader("src/test_instances/d1_50_500.txt");
		
	}
	
}
