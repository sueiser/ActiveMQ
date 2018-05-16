import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;



public class Producer {
    public Producer() {
    }
    public static void sendMessage(int mode, String name, String selector, String msg){
        ActiveMQConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        Destination destination= null;
        MessageProducer messageProducer = null;
        ActiveMQConnection connection=null;
        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
            conn = connectionFactory.createConnection();
            conn.start();

            session = conn.createSession(true,0);
            if(mode == 1)
                destination = session.createQueue(name);

            else
                destination = session.createTopic(name);

            messageProducer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(msg);
            if(!selector.equals(""))
                message.setStringProperty("JMSXGroupID",selector);
            messageProducer.send(message);
            session.commit();
            System.out.println("·¢ËÍ£º"+message.getText());
        } catch (JMSException var15) {
            var15.printStackTrace();
        } finally {
            try {
                session.close();
                conn.close();
            } catch (JMSException var14) {
                var14.printStackTrace();
            }
        }


    }

}
