FriendsOutreach
===============
Helps you to keep in touch with all your friends effectively.
Its all working fine now. So, feel free to use it. 
And, you can suggest feature requests too if you like to see some more functionality in this

What does this program do?
--------------------------
* It helps you to keep in touch with all the important people in your life without ever having to worry about whom to contact when.
* Put all the names of the people you want to stay in touch with, in a file and also the dates on which you have last contacted them (as applies)
* And, just run the program. It will scan through the list and suggest whom you should contact today.

Stuff You need to have before you can run this program
------------------------------------------------------
* Java 8+ installed (Lambdas support required)
* A markdown file with the names of your friends and dates when you last contacted them
* The markdown file should look like the `Demo.markdown` available in the repo

How to run this project
-----------------------
* You can run this like any other Java program that you might have used
* `FriendsOutReachMain.java` is the main class file
* So, to run this, just compile and execute `FriendsOutReachMain.java` and it would suggest a friend for you to contact.

Things you might want to configure
----------------------------------
* Change the **friendsNamesAndDetailsFile** variable in `FriendsOutReachMain.java` to point to your markdown file location
* How many calls per month should be the limit? Configurable in `FriendPicker.java` in the variable *MIN_NUM_OF_FRIENDS_TO_CONTACT_IN_A_MONTH* (Default is 10 friends per month)
* What should be the maximum time after which you would want to call a person again? Configurable via the variable *RECALL_GAP* in `FriendPicker.java`. (Default is 100 days)
