import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Никита on 18.03.2017.
 */
public class StudentMainFrame {
    JFrame studentFrame;
    JLabel[] jLabels;
    ButtonGroup groups[] = new ButtonGroup[20];
    JRadioButton btn[] = new JRadioButton[80];
    ArrayList<Integer> questions = new ArrayList<Integer>();

    public StudentMainFrame() throws SQLException, ClassNotFoundException {
        questions.clear();
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
            panels[i].setLayout(new BoxLayout(panels[i],BoxLayout.Y_AXIS));
            groups[i]=new ButtonGroup();
            panels[i].setVisible(true);
            panels[i].add(new JLabel(getQuestion()));
            for (int j = 0; j < 4; j++) {
                btn[j] = new JRadioButton(generateQuetion(0));
                panels[i].add(btn[j]);
                groups[i].add(btn[j]);
            }
            Const.ANSWER_ID=0;
            container.add(panels[i]);
        }
        JButton answ = new JButton("Завершить");

        //TODO: добавить кнопку "Завершить"


        studentFrame.getContentPane().add(jScrollPane1);
        studentFrame.add(answ,BorderLayout.SOUTH);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(800, 800);
        studentFrame.setVisible(true);
    }

    private String generateQuetion(int questID) {
        String res = null;
        //TODO запрос к бд + получение варинтов ответа, получить ID вопроса можно через наш уже заполненый массив вопросов

        res= String.valueOf(Const.ANSWER_ID);
        Const.ANSWER_ID+=1;
        return res;
    }

    private String getQuestion() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");

        PreparedStatement preparedStaement = conn.prepareStatement("SELECT Question,Right,id FROM QuestionList");
        ResultSet resultSet = preparedStaement.executeQuery();

        PreparedStatement findMax = conn.prepareStatement("SELECT MAX(id) as id FROM QuestionList");
        int max = findMax.executeQuery().getInt("id");


        int k = 0;
        while(k !=1) {
            Random rand = new Random();
            int rnd = rand.nextInt(max);
            if (!questions.contains(rnd) && rnd != 0) {
                System.out.println(rnd);
                questions.add(rnd);
                k++;

                //TODO подключаемся к бд и тырим текст задания зная ID + создаеv HasMap 9 двумерный массив с ответом и ID вопроса

                return String.valueOf(rnd);
            }
        }

        return null;
    }
}
