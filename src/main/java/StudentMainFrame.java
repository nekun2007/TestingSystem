import javax.swing.*;
import java.awt.*;

/**
 * Created by Никита on 18.03.2017.
 */
public class StudentMainFrame {
    JFrame studentFrame;
    JLabel[] jLabels;
    ButtonGroup groups[] = new ButtonGroup[20];
    JRadioButton btn[] = new JRadioButton[80];

    public StudentMainFrame() {
        studentFrame = new JFrame(Const.PROGRAM_NAME);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JScrollPane[] jScrollPane = new JScrollPane[20];
        JPanel[] panels = new JPanel[20];
        JLabel[] labels = new JLabel[20];
        groups = new ButtonGroup[20];
        JScrollPane jScrollPane1 = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        for (int i = 0; i < 20; i++) {
            panels[i] = new JPanel();
            groups[i]=new ButtonGroup();
            panels[i].setVisible(true);
            panels[i].add(new JLabel("Hello #" + i));
            for (int j = 0; j < 4; j++) {
                btn[j] = new JRadioButton("test");
                panels[i].add(btn[j]);
                groups[i].add(btn[j]);
            }
            container.add(panels[i]);
        }

        //TODO: добавить кнопку "Завершить"


        studentFrame.getContentPane().add(jScrollPane1);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(800, 800);
        studentFrame.setVisible(true);
    }

    private void generateQuetion() {
        //TODO: генерация списка вопросов
    }
}
