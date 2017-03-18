import javax.swing.*;
import java.awt.*;

/**
 * Created by Никита on 18.03.2017.
 */
public class StudentMainFrame {
    JFrame studentFrame;
    JPanel[] panels;
    JRadioButton[] jRadioButtons;
    JLabel[] jLabels;
    public StudentMainFrame() {
        studentFrame = new JFrame(Const.PROGRAM_NAME);
        Container container = new Container();
        JScrollPane jScrollPane = new JScrollPane(container);
        panels = new JPanel[20];
        for(int i=0; i<20; i++) {
            panels[i] = new JPanel();
            panels[i].add(new JLabel("Hello #" + i ));
            panels[i].setVisible(true);
            studentFrame.add(panels[i],i);
        }

      // studentFrame.setLayout(new BoxLayout(studentFrame,BoxLayout.Y_AXIS));


        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(800,800);
        studentFrame.setVisible(true);
    }
}
