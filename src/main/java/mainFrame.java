import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by Никита on 18.03.2017.
 */
public class mainFrame extends JFrame {
    private JButton doTeacherFrame;
    private JButton doStudentFrame;
    private JPanel panel1;
    private JButton выходButton;
    public JFrame mainFrameGUI;
    public static String tableName;

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
        final String[] subjArr = new String[]{"ОГП", "Строевая подготовка", "Огневая подготовка", "ВСП+ТСП", "Тактическая подготовка"};
        //new Object[] {"ОГП", "Строевая подготовка", "Огневая подготовка", "ВСП+ТСП", "Тактическая подготовка"}
        doTeacherFrame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selection_subject=(String) JOptionPane.showInputDialog(null,"Выберете дисциплину","Выбор предмета",JOptionPane.PLAIN_MESSAGE, null, subjArr , null);
                if (selection_subject.equals(subjArr[0])){
                    Const.SUBJECT="OGP";
                }
                if (selection_subject.equals(subjArr[1])){
                    Const.SUBJECT="SP";
                }
                if (selection_subject.equals(subjArr[2])){
                    Const.SUBJECT="OPodg";
                }
                if (selection_subject.equals(subjArr[3])){
                    Const.SUBJECT="VSPodg";
                }
                if (selection_subject.equals(subjArr[4])){
                    Const.SUBJECT="TactPodg";
                }
                new TeacherLoginFrame();
                closeFrame();
            }
        });

        doStudentFrame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String selection_subject=(String) JOptionPane.showInputDialog(null,"Выберете дисциплину","Выбор предмета",JOptionPane.PLAIN_MESSAGE, null, subjArr , null);
                if (selection_subject.equals(subjArr[0])){
                    Const.SUBJECT="OGP";
                }
                if (selection_subject.equals(subjArr[1])){
                    Const.SUBJECT="SP";
                }
                if (selection_subject.equals(subjArr[2])){
                    Const.SUBJECT="OPodg";
                }
                if (selection_subject.equals(subjArr[3])){
                    Const.SUBJECT="VSPodg";
                }
                if (selection_subject.equals(subjArr[4])){
                    Const.SUBJECT="TactPodg";
                }
                try {
                    new StudentMainFrame().startTest();
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
