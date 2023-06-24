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

    // send and receive messages via activemq
    public void sendMessage(String groupName) {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic) that messages will be published to
            Topic destination = session.createTopic(groupName);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // Ask user to type a message
            Scanner scanner = new Scanner(System.in);
            AppConstants.print("Type your message: ");
            String messageText = scanner.nextLine();

            // Create a messages
            // String text = "Hello world! From: " + Thread.currentThread().getName() + " :
            // ";
            TextMessage message = session.createTextMessage(messageText);

            // send the message using producer
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
            scanner.close();
        } catch (Exception e) {
            AppConstants.println("Caught: " + e, "red");
            // e.printStackTrace();
        }
    }

    public void viewMessages(String groupName) {

        try {
            // Create a connection factory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the chat group topic
            Topic chatTopic = session.createTopic(groupName);

            // Create the message consumer
            MessageConsumer consumer = session.createConsumer(chatTopic);
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
                            AppConstants.println("[" + time + "] " + sender + ": " + messageText, "green");

                            AppConstants.println(name + " received a message: " + textMessage.getText(), "green");
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
