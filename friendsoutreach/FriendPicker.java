package friendsoutreach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

class FriendPicker {

	private final List<DateFriendLine> dateContactedFriendsObjList;
	private final List<String> allFriendsList;

	//If you don't call some body for 100 days their name will be picked up
	private static final long RECALL_GAP = 100;
	private static final int MIN_NUM_OF_FRIENDS_TO_CONTACT_IN_A_MONTH = 10;

	FriendPicker(final FriendsFile friendsInfoFile) {
		allFriendsList = friendsInfoFile.getAllFriendsList();
		dateContactedFriendsObjList = friendsInfoFile.getDateContactedFriendsObjList();
	}

	public String pickFriendToCall() {
		// areWeGoodForThisMonth condition check here
		if (areWeGoodForThisMonth()) {
			return null;
		}
		String friendPicked;
		List<String> friendsNeverCalled = generateNeverCalledFriendsList();
		if (friendsNeverCalled.size() != 0) {
			friendPicked = friendsNeverCalled.get(generateRndmNonNegIntLessThan(friendsNeverCalled.size() - 1));
		} else {
			friendPicked = pickFriendFromContactedList();
		}
		// System.out.println("Friend Picked to call : " + friendPicked);
		return friendPicked;
	}

	private List<String> generateNeverCalledFriendsList() {

		List<String> fullFriendsList = allFriendsList;
		List<String> contactedFriends = dateContactedFriendsObjList.parallelStream()
				.map(DateFriendLine::getFriend)
				.collect(Collectors.toList());
		fullFriendsList.removeAll(contactedFriends); // removes the contacted friends from all friends
		return fullFriendsList;
	}

	// generates random number from 0 - upperBound. Both inclusive
	private int generateRndmNonNegIntLessThan(final int upperBound) {
		return (int) Math.floor(Math.random() * upperBound + 1);
	}

	private String pickFriendFromContactedList() {
		List<DateFriendLine> friendsToCall = dateContactedFriendsObjList.parallelStream()
				.filter(dateFriend -> numOfDaysBetweenTodayAnd(dateFriend.getDate()) > RECALL_GAP)
				.sorted((obj2, obj1) ->
						numOfDaysSinceLastCalled(obj1.getFriend()) - numOfDaysSinceLastCalled(obj2.getFriend()))
				.collect(Collectors.toList());

		return friendsToCall.get(generateRndmNonNegIntLessThan(friendsToCall.size())).getFriend();
	}

	private int numOfDaysBetweenTodayAnd(final LocalDate date) {
		LocalDate today = LocalDate.now();
		long numOfDays = ChronoUnit.DAYS.between(date, today);
		return (int) numOfDays;
	}

	private int numOfDaysSinceLastCalled(final String friendName) {
		LocalDate dateLastCalled = dateContactedFriendsObjList.parallelStream()
				.filter(dateFriend -> (dateFriend.getFriend().equals(friendName)))
				.map(DateFriendLine::getDate)
				.sorted((date1, date2) -> date2.compareTo(date1)) // Latest call first
				.collect(Collectors.toList())
				.get(0);
		return numOfDaysBetweenTodayAnd(dateLastCalled);
	}

	private int numOfFriendsContactedThisMonth() {
		return (int) dateContactedFriendsObjList.parallelStream()
				.filter(dateFriend ->
						dateFriend.getDate().getYear() == LocalDate.now().getYear()
								&&
								dateFriend.getDate().getMonthValue() == LocalDate.now().getMonthValue()
				)
				.count();
	}

	private boolean areWeGoodForThisMonth() {
		if(numOfFriendsContactedThisMonth() >= MIN_NUM_OF_FRIENDS_TO_CONTACT_IN_A_MONTH) {
			System.out.print("You have contacted " + MIN_NUM_OF_FRIENDS_TO_CONTACT_IN_A_MONTH + " friends this " +
					"month. Do you want	to close the program ? (y/n):");

			BufferedReader reader = null;
			String userInput = "";
			try {
				reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
				userInput = reader.readLine();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// String userInput = new Scanner(System.in, "UTF-8").next();
			if (userInput != null && userInput.equalsIgnoreCase("y")) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}
}
