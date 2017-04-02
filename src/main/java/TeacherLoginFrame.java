import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * Created by Никита on 18.03.2017.
 */
public class TeacherLoginFrame {
    private JPasswordField passwordField1;
    private JButton doLogIn;
    private JButton doExit;
    private JPanel panel1;

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
                if (!passwordField1.getText().equals(Const.PASSWORD)) {
                    JOptionPane.showMessageDialog(null,"Неправильный логин/пароль попробуйте снова","Ошибка логина или пароля",JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        new TeacherMainFrame();
                        loginFrame.dispose();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
