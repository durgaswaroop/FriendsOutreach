package friendsoutreach;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Details about the FriendsIn
class FriendsFile {

    private int friendsContactedStartLineNumber;
    private int friendsOtherInfoStartLineNumber;
    private String friendsInfoFile;
    private List<DateFriendLine> dateContactedFriendsObjList;
    private List<String> allFriendsList;

    FriendsFile(final String friendsInfoFile) {
        instantiateObject(friendsInfoFile);
    }

    private void instantiateObject( final String friendsInfoFile) {
        this.friendsInfoFile =  friendsInfoFile;

        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream(friendsInfoFile), "UTF-8"))) {
            int currentLineNumber = 0;
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                currentLineNumber++;
                if (currentLine.equals("Till Now Contacted")) {
                    friendsContactedStartLineNumber = currentLineNumber + 2;
                } else if (currentLine.equals("Birthdays and other Info")) {
                    friendsOtherInfoStartLineNumber = currentLineNumber + 2;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> fullFile = giveFullFileAsList();
        allFriendsList = generateAllFriendsList(fullFile);
        List<String> dateContactedFriend = generateDateContactedFriendsList(fullFile);
        setDateContactedFriendsObjList(dateContactedFriend);
    }

    private List<String> giveFullFileAsList() {

        List<String> fullFile = new ArrayList<>();
        try {
            fullFile = Files.readAllLines(Paths.get(friendsInfoFile));
        } catch (IOException e) {
            System.out.println("IO Exception occured while reading file");
            e.printStackTrace();
        }
        return fullFile;
    }

    private List<String> generateAllFriendsList(List<String> fullFile) {
        int friendsAllNamesEndLineNumber = friendsContactedStartLineNumber - 4;
        final int friendsAllNamesStartLineNumber = 3;

        // '-1' because the variable values start at 1 while list takes zero indexed
        allFriendsList = fullFile.subList(friendsAllNamesStartLineNumber - 1,
                friendsAllNamesEndLineNumber);
        return allFriendsList;
    }

    private List<String> generateDateContactedFriendsList(final List<String> fullFile) {
        // 4 because of the file structure. Always 4 to get the end
        int friendsContactedEndLineNumber = friendsOtherInfoStartLineNumber - 4;

        // '-1' because the variable values start at 1 while list takes zero indexed
        return fullFile.subList(friendsContactedStartLineNumber - 1,
                friendsContactedEndLineNumber);
    }

    public List<String> getAllFriendsList() {
        return allFriendsList;
    }

    public List<DateFriendLine> getDateContactedFriendsObjList() {
        return dateContactedFriendsObjList;
    }

    private void setDateContactedFriendsObjList(final List<String> dateContactedFriendsList) {
        this.dateContactedFriendsObjList = dateContactedFriendsList.stream()
                .map(DateFriendLine::new)
                .collect(Collectors.toList());
    }


}