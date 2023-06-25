import java.util.Scanner;

public class AppLogic {

    // login method, welcome the user and then asks them to either login or create
    // account
    public static void start() throws Exception {
        // welcome the user and then show them a menu to either login or create account
        // and then call respective functions for the VillageUser Object

        AppConstants.println("Welcome to Village Connect!");
        AppConstants.println("Village Connect is a platform to connect with your village.\n", "green");

        // ask user what they wish to do, Login or SIgn Up and call respective functions
        AppConstants.println("Login/Sign Up to continue:\n");
        AppConstants.println("1. Have an account? Login");
        AppConstants.println("2. Don't have an account? Sign Up");
        AppConstants.println("3. Exit the program");
        AppConstants.print("\nEnter your choice: (1-3):  ");

        // get user input
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        // perform actions according to choice
        if (choice == 1) {
            // login
            VillageUser.login();
        } else if (choice == 2) {
            // sign up
            VillageUser.createAccount();
        } else if (choice == 3) {
            // Exit the program1
            AppConstants.println("\nThank you for using Village Connect. Goodbye!");

            // exit
            System.exit(0);
        } else {
            // invalid choice
            AppConstants.println("Invalid choice. Please try again.", "red");
            start();
        }

        // then go to the main menu
        mainMenu();

        // close the scanner
        scanner.close();
    }

    // menu
    public static void mainMenu() throws Exception {

        // initialize community groups first
        CommunityGroups.initialize();

        // then show menu
        AppConstants.println("\nWhat would you like to do?\n", "white");
        AppConstants.println("1. View Village Groups");
        AppConstants.println("2. Create a Village Group");
        AppConstants.println("3. Log out");

        // Read the user's choice
        AppConstants.print("\nEnter your choice (1-3): ", "white");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Select a group
            CommunityGroups.selectGroup();
        } else if (choice == 2) {
            // Add the newly created group to the list
            CommunityGroups.addGroup();
        } else if (choice == 3) {
            // Exit the program1
            AppConstants.println("\nThank you for using Village Connect. Goodbye!");
            // exit
            System.exit(0);
        } else {
            AppConstants.println("Invalid choice! Choose again.\n");
            // call the main menu again
            mainMenu();
        }

        // close the scanner
        // scanner.close();

    }

}
