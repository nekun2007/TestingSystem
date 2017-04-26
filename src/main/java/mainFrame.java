import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 * Created by Никита on 18.03.2017.
 */
public class mainFrame extends JFrame {
    private JButton doTeacherFrame;
    private JButton doStudentFrame;
    private JPanel panel1;
    private JButton выходButton;
    public JFrame mainFrameGUI;

    public mainFrame() {
        mainFrameGUI = new JFrame("Система тестирования");
        mainFrameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrameGUI.setSize(500, 300);
        mainFrameGUI.setResizable(false);
        mainFrameGUI.add(panel1);
        mainFrameGUI.setVisible(true);
        mainFrameGUI.setLocationRelativeTo(null);
        Image img= Toolkit.getDefaultToolkit().getImage("src/main/java/flag.png");
        mainFrameGUI.setIconImage(img);
        doTeacherFrame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object right_answ=JOptionPane.showInputDialog(null,"Введите вопрос", "Добавление правильного ответа",JOptionPane.PLAIN_MESSAGE, null,new Object[] {"ОГП", "Строевая подготовка", "Огневая подготовка", "ВСП+ТСП", "Тактическая подготовка"}, null);
                switch ()
                new TeacherLoginFrame();
                closeFrame();
            }
        });

        doStudentFrame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new StudentMainFrame();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                closeFrame();
            }
        });

        выходButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

    public void closeFrame() {
        mainFrameGUI.dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
