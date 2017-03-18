

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Никита on 18.03.2017.
 */
public class mainFrame extends JFrame {
    private JButton doTeacherFrame;
    private JButton doStudentFrame;
    private JPanel panel1;
    public JFrame mainFrameGUI;

    public mainFrame() {
        mainFrameGUI = new JFrame("Система тестирования");
        mainFrameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrameGUI.setSize(500, 300);
        mainFrameGUI.setResizable(false);
        mainFrameGUI.add(panel1);
        mainFrameGUI.setVisible(true);

        doTeacherFrame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TeacherLoginFrame();
                closeFrame();
            }
        });

        doStudentFrame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StudentMainFrame();
                closeFrame();
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
