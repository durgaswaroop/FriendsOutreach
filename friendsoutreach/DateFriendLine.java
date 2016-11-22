package friendsoutreach;

import java.time.LocalDate;

/** POJO wrapper for Date|Friend line in the FriendsInfo file
 * Takes in a string of the format date | friendName
 * and converts that to Object with variables 'date' and 'friend'
 */
class DateFriendLine {
    private LocalDate date;
    private String friend;

    public DateFriendLine(String dateFriend) {
        String[] dateFriendArray = dateFriend.split("\\|");
        this.date = parseDateFormat(dateFriendArray[0].trim());
        this.friend = dateFriendArray[1];
    }

    // Input (String) 14-02-2016 -> Output (LocalDate) 02-14-2016
    private LocalDate parseDateFormat(String date) {
        String[] tempAry = date.split("-");
        String temp;
        temp = tempAry[2]+"-"+tempAry[1]+"-"+tempAry[0];
        return LocalDate.parse(temp);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFriend() {
        return friend.trim();
    }
}
