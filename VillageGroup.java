import java.util.*;

public class VillageGroup {
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
    public void showMenu() {

        // group name
        String groupName = this.name;

        AppConstants.println("\n\nWelcome to " + this.name + " Group!\n", "white");
        AppConstants.println("Description:\n" + this.description, "blue");

        // Display the menu options
        AppConstants.println("\nHere are some options to get you started:", "white");
        AppConstants.println("1. View Members " + groupName);
        AppConstants.println("2. Send a Message to " + groupName);
        AppConstants.println("3. Exit the program");

        // Read the user's choice
        AppConstants.print("\nEnter your choice (1-3): ", "white");
        int choice = scanner.nextInt();

        // curremnt user
        VillageUser currentUser = AppConstants.currentUser;

        // logic for the menu
        if (choice == 1) {
            // view members
            printMembers();
            showMenu();

        } else if (choice == 2) {
            // print messages
            printMessages();

            // send a message
            AppConstants.println("\nSend a message to " + groupName);

            // logic for sending a message to the group
            // logic for sending a message to the group
            System.out.print("\nType a message you would like to send to (type 'exit' to go back to the menu)? ");
            String message = scanner.nextLine();

            if (message.equalsIgnoreCase("exit")) {
                showMenu(); // Exit the loop and go back to the menu
            } else {

                // let the user send a message
                currentUser.sendMessage(this.name);

                this.addMessage(message);
            }

        } else if (choice == 3) {
            // exit
            System.exit(0);
        } else {
            AppConstants.println("Invalid choice!", "red");
            showMenu();
        }

    }

    // add message and print other messages
    public void addMessage(String message) {
        this.messages.add(message);
        this.printMessages();
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
        AppConstants.println("\n** " + this.name + "Message Threads **\n");
        for (int i = 0; i < this.messages.size(); i++) {
            AppConstants.println("-" + this.messages.get(i), "cyan");
        }

        System.out.println("\n");
    }
}
