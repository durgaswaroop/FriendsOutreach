import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ArrayKeeper{
    
    LineOperations lop = new LineOperations();
    int contactListStartLineNumber=3; // Line numbers here will start with 1 and not zero
    int contactedListStartLineNumber=0; //44
    int otherInfoStartLineNumber=0; //44
    int currentFriendLine=3;
    int numLines=0;
    int numOfFriendsContactedThisMonth=0;
    ArrayList <String> allFriends = new ArrayList<>();
    HashMap<LocalDate, ArrayList<String>> friendsContacted = new HashMap <LocalDate, ArrayList<String>>();
    
    public ArrayList <String> arrayGenForAllFriends(String listFile){
        
        int[] tempArrayForNoReason = lop.linesCounter(listFile);
        contactedListStartLineNumber=tempArrayForNoReason[1];
        numLines = tempArrayForNoReason[0];
        otherInfoStartLineNumber=tempArrayForNoReason[2];        
        for(int i=contactListStartLineNumber; i<contactedListStartLineNumber; i++)
        {
            //System.out.println("Herer");
            String currentLine="";
            try 
            {
                currentLine = Files.readAllLines(Paths.get(listFile)).get(currentFriendLine - 1);
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            
            if(currentLine.length()!=0){
                //String []tempArray = lineSplitterForTheFriends(
                allFriends.add(currentLine);
            }
            currentFriendLine++;
            //System.out.println("Line number here is :" + i);
        }
        return allFriends;
    }
    
    public HashMap<LocalDate, ArrayList<String>> arrayGenForContactedFriends(String listFile) throws IOException{
        
        LocalDate date;
        String friendName=null;
        ArrayList <String> friendsContactedInOneDay = new ArrayList<String>();
        System.out.println("Contacted List Start Line number : " + contactedListStartLineNumber);
        System.out.println("Other Info Start Line number:" + otherInfoStartLineNumber);
        //System.out.println("Something after this");
        for(int i = contactedListStartLineNumber+2 ; i<otherInfoStartLineNumber-1; i++){
            //System.out.println("I  m herer");
            date = dateFormatter(lop.lineSplitterForTheFriends(listFile, i-1)[0]); //i-1 because it prints Line 54 is we ask for line 53
            friendName = lop.lineSplitterForTheFriends(listFile, i-1)[1];

            if(date.getMonthValue() == LocalDate.now().getMonthValue()){ //keeps track of how many friends contacted this month
                    numOfFriendsContactedThisMonth++;
            }    
            if(friendsContacted.containsKey(date))
            {
                //System.out.println("Some how enterd here. date:"+ date);
                friendsContactedInOneDay = friendsContacted.get(date);
            }
            friendsContactedInOneDay.add(friendName);
            ArrayList <String> fcListCopy = new ArrayList <String> (friendsContactedInOneDay);
            //System.out.println(fcListCopy);
            friendsContacted.put(date, fcListCopy);
            friendsContactedInOneDay.clear();
        }
        return friendsContacted;        
    }
    public boolean areWeGoodForThisMonth(){
        if(numOfFriendsContactedThisMonth >= 10)
                return true;
        
        return false;
    }

    public LocalDate dateFormatter(String date){
        String[] tempAry = date.split("-");
        String temp;
        temp = tempAry[2]+"-"+tempAry[1]+"-"+tempAry[0];
        return LocalDate.parse(temp);
    }
}
