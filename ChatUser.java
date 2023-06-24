import java.util.Scanner;

import javax.jms.*;


public class ChatUser extends VillageUser {
    private Session session;
    private Topic chatTopic;
    private MessageProducer producer;
    private MessageConsumer consumer;

    public ChatUser(String name, String password, Session session, Topic chatTopic) throws JMSException {
        super(password, name);

        this.session = session;
        this.chatTopic = chatTopic;

        // Create the message producer
        producer = session.createProducer(chatTopic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // Create the message consumer
        consumer = session.createConsumer(chatTopic);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println(name + " received a message: " + textMessage.getText());
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    

    // send and receive messages via activemq
    public void sendMessage() {
        try {
       
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
            scanner.close();
        } catch (Exception e) {
            AppConstants.println("Caught: " + e, "red");
            // e.printStackTrace();
        }
    }
}
