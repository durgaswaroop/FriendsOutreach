package friendsoutreach;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class FriendsOutReachMain {

	public static void main(final String[] args) {
		long start = System.nanoTime();

		String friendsInfoFile = getPropertyValueFromFile("friendsInfoFile");

		FriendsFile friendsFile = new FriendsFile(friendsInfoFile);
		FriendPicker friendPicker = new FriendPicker(friendsFile);
		System.out.println("Friend picked to call : " + friendPicker.pickFriendToCall());
		System.out.println("Time taken : " + (System.nanoTime() - start)/1.0e9 + " secs");
	}

	private static String getPropertyValueFromFile(String property) {
		Properties properties = new Properties();
		try(FileInputStream fileStream = new FileInputStream(new File("src/friendsoutreach/input.properties"))) {
			properties.load(fileStream);
			fileStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties.getProperty(property);
	}

	private FriendsOutReachMain() { }

}
