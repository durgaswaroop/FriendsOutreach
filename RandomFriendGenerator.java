import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.time.LocalDate;

public class RandomFriendGenerator {

		DaysDifferenceCalc ddc = new DaysDifferenceCalc();
		Random rnd = new Random();
		long daysTillLastContacted = -100;

		public String friendGenerator(int numFriends,ArrayList<String> friends, 
						HashMap <LocalDate,ArrayList<String>> contactedFriends){

				//int friendsLookedAt = 1;
				String friendPicked = null;
				System.out.println("Num of friends=" + numFriends);
				while(true){
						//System.out.println("Hi here");
						int numGen = rnd.nextInt(numFriends); // Generates a number from 0 to numFriends (exclusive). 
						//So, this can be directly passed to array without manipulating
						String currentPickedFriend = friends.get(numGen);
						

						Optional<Entry<LocalDate, ArrayList<String>>> first 
								= contactedFriends.entrySet()
								.stream()
								.filter(e -> e.getValue().contains(currentPickedFriend))
								.sorted(Map.Entry.<LocalDate, ArrayList<String>>comparingByKey().reversed())
								.findFirst();
						first.ifPresent(entry -> { 
								daysTillLastContacted = ddc.daysDiffCalc(entry.getKey());
						});
						System.out.println(currentPickedFriend);
						System.out.println("days till last contact:" + daysTillLastContacted);

						if(daysTillLastContacted == -100){
								friendPicked = currentPickedFriend;
								break;
						}
						else if(daysTillLastContacted < 100){
								//friendsLookedAt++;
							daysTillLastContacted = -100;
								continue;
						}
						else if(daysTillLastContacted > 100){
								friendPicked = currentPickedFriend;
								break;
								//System.out.println("Friend picked = " + friendPicked);
						}

						//friendsLookedAt++;
						//System.out.println(friendsLookedAt);
				}

				//if(friendPicked == null){
						//friendPicked = friends.get(rnd.nextInt(numFriends));
				//}
				return friendPicked;
		}
}
