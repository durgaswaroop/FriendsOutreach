import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LineOperations {
	
	public int[] linesCounter(String listFile){   // returns the index where the contacted list starts
		int numOfLines=0;
		int contactedListStartLineNumber=0;
		int otherInfoStartLineNumber=0;
		int [] array = new int[3];
		String line;
		try(BufferedReader br = new BufferedReader(new FileReader(listFile))){
			while((line=br.readLine()) != null){
				numOfLines++;				
				if(line.equals("Till Now Contacted"))
					contactedListStartLineNumber = numOfLines; //to adjust for the start with 1 
				else if(line.equals("Birthdays and other Info"));
					otherInfoStartLineNumber = numOfLines;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		array[0] = numOfLines; // 1st value has num of lines
		array[1] = contactedListStartLineNumber; // 2nd value has the num of lines in the entire friends list
		array[2] = otherInfoStartLineNumber;
		//System.out.println("list start:"+array[1]);
		return array;
	}
	
	public String[] lineSplitterForTheFriends(String listFile, int currentLineNumInContactedList){
		String currentLine = null;
		String [] tempArray = new String[2];
		//System.out.println("current Line Num In Contacted List:" + currentLineNumInContactedList);
		try 
		{
			currentLine = Files.readAllLines(Paths.get(listFile)).get(currentLineNumInContactedList);
		} 
		catch (IOException e) 
		{
			//System.out.println("Printed from here")
			e.printStackTrace();
		}
		System.out.println("Current line in the method:"+ currentLine);
		tempArray = currentLine.split("\\|");
		tempArray[0]=tempArray[0].trim();
		tempArray[1]=tempArray[1].trim();
		//System.out.println("Size of the array here is:" + tempArray.length);
		return tempArray;
		
	}
	//public void lineSplitterForAllFriends(String listFile, int currentLineNumInAllFriendsList){
			//String currentLine = null;
			//String [] tempArray;
			
			//try
			//{
				//currentLine = Files.readAllLines(Paths.get(listFile)).get(currentLineNumInAllFriendsList);
			//}
			//catch (IOException e){
					//e.printStackTrace();
			//}
			//tempArray = currentLine.split("\\|");
	//}

}
