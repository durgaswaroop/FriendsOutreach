import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Titanic {

	public static void main(String[] args) throws IOException {
		
		String listFile = "C:\\Users\\dperla\\Desktop\\friends_reachout.markdown";
		ArrayKeeper ark = new ArrayKeeper();
		RandomFriendGenerator rfg = new RandomFriendGenerator();
		//Scanner scnr = new Scanner(System.in);
		ArrayList <String> friends =  ark.arrayGenForAllFriends(listFile); //puts all the names of friends in a list
		System.out.println(friends);
		HashMap<LocalDate, ArrayList<String>> contactedFriends = ark.arrayGenForContactedFriends(listFile);
		//System.out.println(contactedFriends);

		if(ark.areWeGoodForThisMonth()){
				System.out.println("heere:");
				System.out.println("Looks like we are good for this month. Do you still want to continue? [Yes(y/Y) No(n/N)]");
				if(System.in.read()== 'n' || System.in.read()== 'N' )
					System.exit(0);
		}

		String friendPicked = rfg.friendGenerator(friends.size(), friends, contactedFriends);
		System.out.println(friends.size());
		//System.out.println("Friends All" + friends);
		System.out.println("Friends contacted till now:" + contactedFriends);
		System.out.println("Friend Picked:" + friendPicked);
	}
}
