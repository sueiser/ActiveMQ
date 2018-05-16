//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
import javax.jms.*;
import javax.swing.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.RedeliveryPolicy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Consumer {
    public static void Receive(int mode, int ack, String name, String clientId, String selector, JTextArea txtReceive, JButton btn) throws JMSException{
        ActiveMQConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        MessageConsumer messageConsumer = null;
        Topic topic = null;
        Queue queue = null;

        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);

            //�����ش�����
            RedeliveryPolicy policy = new RedeliveryPolicy();
            policy.setUseExponentialBackOff(false);
            policy.setMaximumRedeliveries(3);
            policy.setInitialRedeliveryDelay(10);
            policy.setRedeliveryDelay(1000);
            policy.setMaximumRedeliveryDelay(2000);
            connectionFactory.setRedeliveryPolicy(policy);
            conn = connectionFactory.createConnection();

            if(mode==2) {
                //����ClientId
                conn.setClientID(clientId);
            }
            conn.start();
            if (ack==1){
                session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            }
            else{
                session = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            }
            if(mode==1) {
                queue = session.createQueue(name);
                if(!selector.equals(""))
                    messageConsumer = session.createConsumer(queue,"JMSXGroupID='" + selector+"'");
                else
                    messageConsumer = session.createConsumer(queue);
            }
            else {
                topic = session.createTopic(name);
                messageConsumer = session.createDurableSubscriber(topic, clientId); //�־ö���
            }

            messageConsumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    if (message instanceof BlobMessage) {
                        BlobMessage blobMessage = (BlobMessage) message;
                        try {
                            String fileName = blobMessage.getStringProperty("FILE.NAME");
                            System.out.println("�ļ�����������" + fileName + "���ļ���С��"
                                    + blobMessage.getLongProperty("FILE.SIZE") + " �ֽ�");
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("��ָ���ļ�����λ��");
                            fileChooser.setSelectedFile(new File(fileName));
                            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                OutputStream os = new FileOutputStream(file);
                                System.out.println("��ʼ�����ļ���" + fileName);
                                InputStream inputStream = blobMessage.getInputStream();
                                //д�ļ�����Ҳ����ʹ��������ʽ
                                byte[] buff = new byte[256];
                                int len = 0;
                                while ((len = inputStream.read(buff)) > 0) {
                                    os.write(buff, 0, len);
                                }
                                os.close();
                                System.out.println("����ļ����գ�" + fileName);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
//                            Producer.sendMessage(1,name+"#SEND",((TextMessage) message).getText());
//                            Producer.sendMessage(1,name+"#RECEIVE",((TextMessage) message).getText());
                            btn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        message.acknowledge();
                                    }catch (Exception e1){
                                        e1.printStackTrace();
                                    }
                                }
                            });
                            String msg = null;
                            if(mode==1) {
                                String s=message.getStringProperty("JMSXGroupID");
                                if(s!=null)
                                    msg ="���յ�����"+ name +"����Ϊ" + s + "����Ϣ��" + ((TextMessage) message).getText();
                                else
                                    msg ="���յ�����"+ name + "����Ϣ��" + ((TextMessage) message).getText();
                            }
                            else {
                                msg ="���յ�" + clientId + "����topic:" + name + "����Ϣ��" + ((TextMessage) message).getText();
                            }
                            System.out.println(msg);
                            txtReceive.setText(txtReceive.getText() + msg + "\n");
                        }
                        catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
