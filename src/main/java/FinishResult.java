import javax.swing.*;
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
        finish.setSize(700,500);
        finish.setResizable(false);
        finish.setVisible(true);
    }
}