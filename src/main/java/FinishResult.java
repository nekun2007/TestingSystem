import javax.swing.*;

/**
 * Created by Никита on 26.03.2017.
 */
public class FinishResult {
    public JTextArea textArea1;
    private JPanel panel1;

    public void showFinishResult() {
        JFrame finish = new JFrame("Тест заврешен");
        finish.add(panel1);
        finish.setSize(500,500);
        finish.setResizable(false);
        finish.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        finish.setVisible(true);
    }
}
