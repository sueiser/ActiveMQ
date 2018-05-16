import javax.jms.JMSException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ProducerFrame {

    public static void main(String[] args) {
        // 1. ����һ���������������ڣ�
        JFrame jf = new JFrame("Producer");          // ��������
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

        JComboBox comboBox=new JComboBox();
        comboBox.addItem("topic");
        comboBox.addItem("queue");
        comboBox.setBounds(90,0,120,25);
        comboBox.setBackground(Color.WHITE);
        panel.add(comboBox);

        JLabel lblName = new JLabel("T or Q Name:");
        lblName.setBounds(0,30,80,25);
        panel.add(lblName);

        JTextField txtName = new JTextField(20);
        txtName.setBounds(90,30,120,25);
        panel.add(txtName);



        JLabel lblSelector = new JLabel("JMSSelector:");
        lblSelector.setBounds(0,60,80,25);
        panel.add(lblSelector);

        JTextField txtSelector = new JTextField(20);
        txtSelector.setBounds(90,60,120,25);
        panel.add(txtSelector);

        JLabel lblMessage = new JLabel("Message:");
        lblMessage.setBounds(0,90,600,25);
        panel.add(lblMessage);

        JTextArea txtMessage = new JTextArea();
        txtMessage.setBounds(0,120,600,100);
        panel.add(txtMessage);

        JLabel lblMsg = new JLabel("");
        lblMsg.setBounds(200,230,200,25);
        panel.add(lblMsg);

        JButton btnSendMsg = new JButton("������Ϣ");
        btnSendMsg.setBounds(370,230,100,25);
        btnSendMsg.setBackground(Color.WHITE);
        panel.add(btnSendMsg);
        JButton btnSendFile = new JButton("�����ļ�");
        btnSendFile.setBounds(480,230,100,25);
        btnSendFile.setBackground(Color.WHITE);
        panel.add(btnSendFile);
        //��ʼ��һ����������
        JTextArea txtReceive = new JTextArea();
        panel.add(txtReceive);
        // 4. �� ������� ��Ϊ���ڵ�������� ���õ� ����
        jf.setContentPane(panel);
        // 5. ��ʾ���ڣ�ǰ�洴������Ϣ�����ڴ��У�ͨ�� jf.setVisible(true) ���ڴ��еĴ�����ʾ����Ļ�ϡ�
        jf.setVisible(true);

        btnSendMsg.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event) {
                if(comboBox.getSelectedItem().toString().equals("queue") ) {
                    Producer.sendMessage(1,txtName.getText(),txtSelector.getText(), txtMessage.getText());
                    lblMsg.setText("���ͣ�"+txtMessage.getText());
                }
                else {
                    Producer.sendMessage(2,txtName.getText(),txtSelector.getText(),txtMessage.getText());
                    lblMsg.setText("���ͣ�"+txtMessage.getText());

                }
            }
        });

        btnSendFile.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event) {
                if(comboBox.getSelectedItem().toString() == "queue") {
                    try {
                        FileSender.SendFile(1, txtName.getText());
                    }
                    catch (JMSException e){
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        FileSender.SendFile(2, txtName.getText());
                    }
                    catch (JMSException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        jf.setContentPane(panel);

        jf.setVisible(true);
    }

}