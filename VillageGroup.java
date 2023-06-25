import java.io.Serializable;
import java.util.*;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class VillageGroup implements Serializable {
    // name
    public String name;
    // description
    public String description;
    // members usernames
    public List<String> members;
    // messages
    public List<String> messages;
    // admin
    public String adminUsername;

    Scanner scanner = new Scanner(System.in);

    // constructor
    public VillageGroup(String name, String description, String admin) {
        this.name = name;
        this.description = description;
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.adminUsername = admin;

        this.messages.add("Welcome to " + name + "!");
        this.messages.add("Please use this group to share important announcements and updates.");

    }

    // to string method
    public String toString() {
        return "Name: " + this.name + "\nDescription: " + this.description + "\nAdmin: " + this.adminUsername;
    }

    // join group adds user id to members list if they don't already exist
    public void addUser(String userId) {
        if (!this.members.contains(userId)) {
            this.members.add(userId);
        }
    }

    // leave group removes user id from members list if they exist
    public void removeUser(String userId) {
        if (this.members.contains(userId)) {
            this.members.remove(userId);
        }
    }

    // Show group menu function, takes in the selected group and displays the menu
    // options
    public void showMenu() throws Exception {

        // group name
        String groupName = this.name;

        AppConstants.println("\n\nWelcome to " + groupName + " Group!\n", "yellow");
        AppConstants.println("Description:\n" + this.description, "cyan");

        // Display the menu options
        AppConstants.println("\nHere are some options to get you started with The " + this.name + " Group!\n", "white");
        AppConstants.println("1. View Members " + groupName);
        AppConstants.println("2. Send a Message to " + groupName);
        AppConstants.println("3. Listen to Messages from " + groupName);
        AppConstants.println("4. Log Out");

        // Read the user's choice
        AppConstants.print("\nEnter your choice (1-4): ", "white");
        int choice = scanner.nextInt();

        // curremnt user
        VillageUser currentUser = AppConstants.currentUser;

        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(AppConstants.BROKER_URL);

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic) that messages will be published to
        Topic destination = session.createTopic(groupName);

        // chat user
        ChatUser chatUser = new ChatUser(currentUser.name, currentUser.password, session, destination);

        // logic for the menu
        if (choice == 1) {
            // view members
            printMembers();
            showMenu();
        } else if (choice == 2) {
            String message;
            do { // send a message
                AppConstants.println("\n> Send a message to " + groupName);

                // let the user send a message
                message = chatUser.sendMessage();

                addMessage(message);
            } while (message != "exit");

            // close session and go to Menu
            // Clean up
            session.close();
            connection.close();
            
            // go back to menu
            showMenu();

        } else if (choice == 3) {
            // print messages
            printMessages();
            // view and listen to messages sent to the group
            chatUser.viewMessages();

            AppConstants.println("\n> Waiting for other messages from " + groupName + "...");
            exitToMenu();
        } else if (choice == 4) {
            // exit
            System.exit(0);
        } else {
            AppConstants.println("Invalid choice!", "red");
            showMenu();
        }

    }

    public void exitToMenu() throws Exception {
        // tell user to type exit to go back to menu
        AppConstants.println("\nCan't wait anymore? Type 'exit' to go back to group menu", "red");
        String exit = scanner.next();

        // if user types exit, go back to menu
        if (exit.equals("exit")) {
            showMenu();
        } else {

        }
    }

    // add message and print other messages
    public void addMessage(String message) {
        messages.add(message);
    }

    // print members
    public void printMembers() {
        AppConstants.println("\n** " + this.name + " Members **\n");
        for (int i = 0; i < this.members.size(); i++) {
            AppConstants.println("-" + this.members.get(i));
        }
    }

    // print messages
    public void printMessages() {
        AppConstants.println("\n** " + this.name + " Messages **\n");
        for (int i = 0; i < this.messages.size(); i++) {
            AppConstants.println("-" + this.messages.get(i), "cyan");
        }

        System.out.println("\n");
    }

    //
}
