

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        try {
            // Create a connection factory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the chat group topic
            Topic chatTopic = session.createTopic("VillageConnect");

            // Prompt for the admin's name
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter admin's name: ");
            String adminName = scanner.nextLine();

            // Create the chairman as the admin
            ChatUserx admin = new ChatUserx(adminName, session, chatTopic);
            admin.joinGroup();

            // Prompt for other users' names
            System.out.print("Enter user1's name: ");
            String user1Name = scanner.nextLine();
            System.out.print("Enter user2's name: ");
            String user2Name = scanner.nextLine();

            // Create other users
            ChatUserx user1 = new ChatUserx(user1Name, session, chatTopic);
            ChatUserx user2 = new ChatUserx(user2Name, session, chatTopic);
            user1.joinGroup();
            user2.joinGroup();

            // Prompt for and send messages
            System.out.print("Enter welcome message: ");
            String welcomeMessage = scanner.nextLine();
            admin.sendMessage(welcomeMessage);

            System.out.print("Enter message from user1: ");
            String user1Message = scanner.nextLine();
            user1.sendMessage(user1Message);

            System.out.print("Enter message from user2: ");
            String user2Message = scanner.nextLine();
            user2.sendMessage(user2Message);

            // Remove a user from the group
            user1.leaveGroup();

            // Prompt for and send a message after user1 has left
            System.out.print("Enter message from user2 after user1 has left: ");
            String user2MessageAfterLeave = scanner.nextLine();
            user2.sendMessage(user2MessageAfterLeave);

            // Close the connections
            session.close();
            connection.close();
            scanner.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
