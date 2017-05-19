import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by exluap on 18.05.17.
 */
public class printTest implements ActionListener {
    private String[] alphabet = {"а)", "б)", "в)", "г)"};
    private StudentMainFrame studentMainFrame = new StudentMainFrame();

    public void actionPerformed(ActionEvent e) {
        try {
            GenerateTest();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Генерация теста
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void GenerateTest() throws SQLException, ClassNotFoundException {
        for (int i=0; i < 20; i++) {
            System.out.println((i+1) + ". " + studentMainFrame.getQuestion());
            for (int j =0; j < 4; j++) {
                System.out.print(alphabet[j] + studentMainFrame.generateQuetion(i) + "\n");
            }
            System.out.print("Верный ответ: " + studentMainFrame.faq.get(i) + "\n");
            Const.ANSWER_ID = 0;
        }
    }
}
