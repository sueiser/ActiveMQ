import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConsumerFrame {

    public static void main(String[] args) throws InterruptedException {
        // 1. ����һ���������������ڣ�
        JFrame jf = new JFrame("ConsumerA");          // ��������
        jf.setSize(600, 300);                       // ���ô��ڴ�С
        jf.setLocationRelativeTo(null);             // �Ѵ���λ�����õ���Ļ����
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // ��������ڵĹرհ�ťʱ�˳�����û����һ�䣬���򲻻��˳���

        // 2. �����м����������������
        JPanel panel = new JPanel();                // �������������ʹ��Ĭ�ϵĲ��ֹ�����
        panel.setLayout(null);
        /*
         * ���������˵������û�ѡ��
         */
        JLabel lblTQ = new JLabel("Topic or Queue:");
        lblTQ.setBounds(0,0,100,25);
        panel.add(lblTQ);

        JComboBox comTorQ=new JComboBox();
        comTorQ.addItem("topic");
        comTorQ.addItem("queue");
        comTorQ.setBounds(90,0,120,25);
        comTorQ.setBackground(Color.WHITE);
        panel.add(comTorQ);

        JLabel lblTopic = new JLabel("TopicName:");
        lblTopic.setBounds(0,30,100,25);
        panel.add(lblTopic);

        JTextField txtTopic = new JTextField(20);
        txtTopic.setBounds(90,30,120,25);
        panel.add(txtTopic);

        JLabel lblClientId = new JLabel("ClientId:");
        lblClientId.setBounds(0,60,100,25);
        panel.add(lblClientId);

        JTextField txtClintId = new JTextField(20);
        txtClintId.setBounds(90,60,120,25);
        panel.add(txtClintId);

        JLabel lblQueue = new JLabel("QueueName:");
        lblQueue.setBounds(0,30,100,25);
        lblQueue.setVisible(false);
        panel.add(lblQueue);

        JTextField txtQueue = new JTextField(20);
        txtQueue.setBounds(90,30,120,25);
        txtQueue.setVisible(false);
        panel.add(txtQueue);

        JLabel lblJMSSelector = new JLabel("JMSSelector:");
        lblJMSSelector.setBounds(0,60,100,25);
        lblJMSSelector.setVisible(false);
        panel.add(lblJMSSelector);

        JTextField txtJMSSelector = new JTextField(20);
        txtJMSSelector.setBounds(90,60,120,25);
        txtJMSSelector.setVisible(false);
        panel.add(txtJMSSelector);

        JLabel lblAcknowledge = new JLabel("Acknowledge:");
        lblAcknowledge.setBounds(0,90,100,25);
        panel.add(lblAcknowledge);

        JComboBox comAck=new JComboBox();
        comAck.addItem("AUTO_ACKNOWLEDGE");
        comAck.addItem("CLIENT_ACKNOWLEDGE");
        comAck.setBounds(90,90,120,25);
        comAck.setBackground(Color.WHITE);
        panel.add(comAck);

        JButton btnAck = new JButton("ȷ�Ͻ���");
        btnAck.setBounds(90,120,120,25);
        btnAck.setBackground(Color.WHITE);
        btnAck.setVisible(false);
        panel.add(btnAck);

        JLabel lblMsg = new JLabel("");
        lblMsg.setBounds(350,230,200,25);
        panel.add(lblMsg);

        //��ʼ��һ����������
        JTextArea txtReceive = new JTextArea();
        txtReceive.setBounds(220,3,360,220);
        panel.add(txtReceive);

        JButton btnReceive = new JButton("��������");
        btnReceive.setBounds(470,230,110,25);
        btnReceive.setBackground(Color.WHITE);
        panel.add(btnReceive);

        //�� ������� ��Ϊ���ڵ�������� ���õ� ����
        jf.setContentPane(panel);
        //��ʾ���ڣ�ǰ�洴������Ϣ�����ڴ��У�ͨ�� jf.setVisible(true) ���ڴ��еĴ�����ʾ����Ļ�ϡ�
        jf.setVisible(true);

        btnReceive.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                if(comTorQ.getSelectedItem().toString() == "queue") {
                    if (txtQueue.getText().equals("")) {
                        lblMsg.setText("������QueueName");
                    } else {
                        try {
                            int ack=1;
                            if(comAck.getSelectedItem().toString().equals("CLIENT_ACKNOWLEDGE"))
                                ack=2;
                            Consumer.Receive(1, ack, txtQueue.getText(), null, txtJMSSelector.getText(), txtReceive, btnAck);
                            lblMsg.setText("�����ɹ�");
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    if(txtTopic.getText().equals("")) {
                        lblMsg.setText("������TopicName");
                    } else {
                        if(txtClintId.getText().equals("")){
                            lblMsg.setText("������ClintId");
                        }
                        else {
                            try {
                                int ack = 1;
                                if (comAck.getSelectedItem().toString().equals("CLIENT_ACKNOWLEDGE"))
                                    ack = 2;
                                lblMsg.setText("");
                                Consumer.Receive(2, ack, txtTopic.getText(), txtClintId.getText(), null, txtReceive, btnAck);
                                lblMsg.setText("�����ɹ�");

                            } catch (JMSException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        comTorQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comTorQ.getSelectedItem().toString().equals("topic")){
                    lblQueue.setVisible(false);
                    txtQueue.setVisible(false);
                    lblJMSSelector.setVisible(false);
                    txtJMSSelector.setVisible(false);
                    lblTopic.setVisible(true);
                    txtTopic.setVisible(true);
                    lblClientId.setVisible(true);
                    txtClintId.setVisible(true);
                }
                else {
                    lblQueue.setVisible(true);
                    txtQueue.setVisible(true);
                    lblJMSSelector.setVisible(true);
                    txtJMSSelector.setVisible(true);
                    lblTopic.setVisible(false);
                    txtTopic.setVisible(false);
                    lblClientId.setVisible(false);
                    txtClintId.setVisible(false);
                }
            }
        });
        comAck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comAck.getSelectedItem().toString().equals("AUTO_ACKNOWLEDGE")){
                    btnAck.setVisible(false);
                }
                else {
                    btnAck.setVisible(true);
                }
            }
        });
        btnAck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }
}