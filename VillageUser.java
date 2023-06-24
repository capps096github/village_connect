import java.io.*;
import java.util.Scanner;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class VillageUser implements Serializable {
    // user password
    public String password;
    // name
    public String name;

    // constructor
    public VillageUser(String password, String name) {
        this.password = password;
        this.name = name;
    }

    // to string method
    @Override
    public String toString() {
        return "Logged In as: @" + this.name + " - password: " + this.password + "\n";
    }

    // Authentication method to authenticate user
    public static void login() {
        // Get user input variables: String inputName, String inputPassword using
        // Scanner
        Scanner scanner = new Scanner(System.in);
        AppConstants.println("\nVillage Connect Login ----------------------");
        AppConstants.print("Enter your username: @");
        String inputName = scanner.nextLine();
        AppConstants.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        // get the existing User details
        VillageUser existingUser = AppUsers.getCurrentUser(inputName, inputPassword);

        if (existingUser != null) {
            // update the current user
            AppConstants.currentUser = existingUser;

            //
            AppConstants.println("Authentication successful!", "green");
            AppConstants.println("Logged In as: @" + existingUser.name.toLowerCase(), "green");
            // User exists
            AppConstants.println("\n\nWelcome back, " + existingUser.name.toUpperCase() + " to VillageConnect!");

            // call the main menu
        } else {
            // User does not exist, create a new account
            AppConstants.println("Account does not exist. Please create an account.", "red");

            // call create account method
            createAccount();
        }

        // scanner.close();
    }

    // create account method
    // this asks for user input and creates a new account
    public static void createAccount() {
        // Get user input variables: String inputName, String inputPassword using
        // Scanner
        Scanner scanner = new Scanner(System.in);
        AppConstants.println("\nVillage Connect Sign Up ----------------------");
        AppConstants.print("Enter your username: @");
        String inputName = scanner.nextLine();
        AppConstants.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        VillageUser newUser = new VillageUser(inputPassword, inputName);

        // update the current user
        AppConstants.currentUser = newUser;

        // add user to the list of users

        // serialize the user
        AppUsers.serializeUsers(newUser);

        // close the scanner
        // scanner.close();

        // Print success message
        AppConstants.println("Account created successfully!", "green");
        // User exists
        AppConstants.println("\n\nWelcome, " + newUser.name.toUpperCase() + " to VillageConnect!\n");

        // * call the main menu

    }

    // public void viewMessages(String groupName) {
    public void viewMessages(Session session, Topic destination) {

        try {

            MessageConsumer consumer = session.createConsumer(destination);

            // Message message = consumer.receive(1000);

            // if (message instanceof TextMessage) {
            // TextMessage textMessage = (TextMessage) message;
            // String text = textMessage.getText();
            // System.out.println("Received: " + text);
            // } else {
            // System.out.println("Received: " + message);
            // }
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            TextMessage textMessage = (TextMessage) message;
                            // message details like sender, time, message
                            String sender = textMessage.getStringProperty("sender");
                            String time = textMessage.getStringProperty("time");
                            String messageText = textMessage.getText();

                            // print the message
                            AppConstants.println("[" + time + "] " + sender + ": " + messageText,
                                    "green");

                            AppConstants.println(name + " received a message: " + textMessage.getText(),
                                    "green");
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            AppConstants.println("Caught: " + e, "red");

        }

    }

}
