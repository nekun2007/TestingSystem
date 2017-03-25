import javax.swing.*;
import java.awt.*;

/**
 * Created by Никита on 18.03.2017.
 */
public class StudentMainFrame {
    JFrame studentFrame;
    JRadioButton[] jRadioButtons;
    JLabel[] jLabels;
    ButtonGroup[] groups;
    public StudentMainFrame() {
        studentFrame = new JFrame(Const.PROGRAM_NAME);
       JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        JScrollPane[] jScrollPane = new JScrollPane[20];
        JPanel[] panels = new JPanel[20];
        JLabel[] labels = new JLabel[20];
        groups = new ButtonGroup[80];
        JScrollPane jScrollPane1 = new JScrollPane(container,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        for(int i=0; i<20; i++) {
            panels[i] = new JPanel();
            panels[i].setVisible(true);
            panels[i].add(new JLabel("Hello #" + i));
            groups[i] = new ButtonGroup();

            groups[i].add(new JRadioButton("test1"));
            groups[i].add(new JRadioButton("test1"));
            groups[i].add(new JRadioButton("test1"));
            groups[i].add(new JRadioButton("test1"));
            panels[i].add();
            container.add(panels[i]);
        }

        //TODO: добавить кнопку "Завершить"


        studentFrame.getContentPane().add(jScrollPane1);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(800,800);
        studentFrame.setVisible(true);
    }

    private void generateQuetion() {
        //TODO: генерация списка вопросов
    }
}
