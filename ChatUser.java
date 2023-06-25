import java.util.Scanner;

import javax.jms.*;

public class ChatUser extends VillageUser {
    private Session session;
    private Topic chatTopic;
    private MessageProducer producer;

    public ChatUser(String name, String password, Session session, Topic chatTopic) throws JMSException {
        super(password, name);

        this.session = session;
        this.chatTopic = chatTopic;

        // Create the message producer
        producer = session.createProducer(chatTopic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

    }

    // send and receive messages via activemq
    public String sendMessage() {
        String messageText = "";
        try {

            // Ask user to type a message
            Scanner scanner = new Scanner(System.in);
            AppConstants.print("Type your message or 'exit' to go back to group menu: ");
            messageText = scanner.nextLine();

            // Create a messages
            TextMessage message = session.createTextMessage(messageText);

            // message details
            String sender = name;
            String time = AppConstants.getCurrentTime();

            // set the message properties
            message.setStringProperty("sender", sender);
            message.setStringProperty("time", time);

            // send the message using producer
            producer.send(message);

            // print the message
            AppConstants.printSuccess("\n> Message Sent at [" + time + "] by " + sender + ": " + messageText);

        } catch (Exception e) {
            AppConstants.println("Caught: " + e, "red");
        }

        // return the message
        return messageText;
    }

    // this is used to listen to messages from the topic
    public void viewMessages() {

        try {

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
                            AppConstants.println("\n> [" + time + "] @" + sender + " sent: " + messageText, "cyan");

                            AppConstants.println("> @" + name + " (you) received: " + textMessage.getText(),
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
