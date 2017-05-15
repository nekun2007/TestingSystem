import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Created by Никита on 18.03.2017.
 */
public class TeacherLoginFrame {
    private JPasswordField passwordField1;
    private JButton doLogIn;
    private JButton doExit;
    private JPanel panel1;
    private JTextField logField;

    public TeacherLoginFrame() {
        final JFrame loginFrame = new JFrame(Const.PROGRAM_NAME);
        loginFrame.setSize(500,300);
        loginFrame.add(panel1);
        loginFrame.setVisible(true);
        loginFrame.setLocationRelativeTo(null);
        Image img= Toolkit.getDefaultToolkit().getImage("src/main/java/flag.png");
        loginFrame.setIconImage(img);

        doExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new mainFrame();
                loginFrame.dispose();
            }
        });

        loginFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new mainFrame();
            }
        });

        doLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT login, password FROM Subject WHERE subjName=?");
                    preparedStatement.setString(1, Const.SUBJECT);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()) {
                        if (resultSet.getString("login").equals(logField.getText()) && resultSet.getString("password").equals(passwordField1.getText())) {
                            new TeacherMainFrame();
                            loginFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null,"Ошибка при вводе логина или пароля");
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Action logIn  = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT login, password FROM Subject WHERE subjName=?");
                    preparedStatement.setString(1, Const.SUBJECT);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()) {
                        if (resultSet.getString("login").equals(logField.getText()) && resultSet.getString("password").equals(passwordField1.getText())) {
                            new TeacherMainFrame();
                            loginFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null,"Ошибка при вводе логина или пароля");
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        };

        panel1.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"clickButton");
        panel1.getRootPane().getActionMap().put("clickButton", logIn);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
