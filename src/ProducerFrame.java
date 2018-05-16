import javax.jms.JMSException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ProducerFrame {

    public static void main(String[] args) {
        // 1. 创建一个顶层容器（窗口）
        JFrame jf = new JFrame("Producer");          // 创建窗口
        jf.setSize(600, 300);                       // 设置窗口大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）

        // 2. 创建中间容器（面板容器）
        JPanel panel = new JPanel();                // 创建面板容器，使用默认的布局管理器
        panel.setLayout(null);
        /*
         * 创建下拉菜单用于用户选择
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

        JButton btnSendMsg = new JButton("发送信息");
        btnSendMsg.setBounds(370,230,100,25);
        btnSendMsg.setBackground(Color.WHITE);
        panel.add(btnSendMsg);
        JButton btnSendFile = new JButton("发送文件");
        btnSendFile.setBounds(480,230,100,25);
        btnSendFile.setBackground(Color.WHITE);
        panel.add(btnSendFile);
        //初始化一个文字区域
        JTextArea txtReceive = new JTextArea();
        panel.add(txtReceive);
        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        jf.setContentPane(panel);
        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);

        btnSendMsg.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event) {
                if(comboBox.getSelectedItem().toString().equals("queue") ) {
                    Producer.sendMessage(1,txtName.getText(),txtSelector.getText(), txtMessage.getText());
                    lblMsg.setText("发送："+txtMessage.getText());
                }
                else {
                    Producer.sendMessage(2,txtName.getText(),txtSelector.getText(),txtMessage.getText());
                    lblMsg.setText("发送："+txtMessage.getText());

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