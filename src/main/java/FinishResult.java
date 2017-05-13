import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Никита on 26.03.2017.
 */
public class FinishResult {
    public JTextArea textArea1;
    private JPanel panel1;

    public void showFinishResult() {
        final JFrame finish = new JFrame("Тест заврешен");
        finish.add(panel1);
        finish.setUndecorated(true);
        finish.setSize(800, 800);
        finish.setVisible(true);
        finish.setLocationRelativeTo(null);
        finish.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image img = Toolkit.getDefaultToolkit().getImage("src/main/java/flag.png");
        finish.setIconImage(img);
        finish.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
