import java.io.File;
import javax.jms.*;
import javax.swing.JFileChooser;
import org.apache.activemq.*;
/**
 * ͨ�� ActiveMQ �����ļ��ĳ���
 * @author Unmi
 */
public class FileSender {
    /**
     * @throws JMSException
     */
    public static void SendFile(int mode, String name) throws JMSException {
        // ѡ���ļ�
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("��ѡ��Ҫ���͵��ļ�");
        if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = fileChooser.getSelectedFile();
        // ��ȡ ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8161/fileserver/");
        // ���� Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // ���� Session
        ActiveMQSession session = (ActiveMQSession) connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);
        // ���� Destination
        Destination destination = null;
        if(mode==1)
            destination = session.createQueue(name);
        else
            destination = session.createTopic(name);
        // ���� Producer
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);// ����Ϊ�ǳ־���
        // ���ó־��ԵĻ����ļ�Ҳ�����Ȼ������������ն�����������Ҳ�����յ��ļ�

        // ���� BlobMessage�����������ļ�
        BlobMessage blobMessage = session.createBlobMessage(file);
        blobMessage.setStringProperty("FILE.NAME", file.getName());
        blobMessage.setLongProperty("FILE.SIZE", file.length());
        System.out.println("��ʼ�����ļ���" + file.getName() + "���ļ���С��"
                + file.length() + " �ֽ�");

        // 7. �����ļ�
        producer.send(blobMessage);
        System.out.println("����ļ����ͣ�" + file.getName());
        producer.close();
        session.close();
        connection.close(); // ���ر� Connection, �������˳�

    }

}