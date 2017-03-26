import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Никита on 18.03.2017.
 */
public class StudentMainFrame {
    JFrame studentFrame;
    ButtonGroup groups[] = new ButtonGroup[20];
    JRadioButton btn[] = new JRadioButton[80];
   public ArrayList<Integer> questions = new ArrayList<Integer>();
    public HashMap<Integer, Integer> faq = new HashMap<Integer, Integer>();

    public StudentMainFrame() throws SQLException, ClassNotFoundException {
        questions.clear();
        faq.clear();
        studentFrame = new JFrame(Const.PROGRAM_NAME);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel[] panels = new JPanel[20];
        groups = new ButtonGroup[20];
        JScrollPane jScrollPane1 = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        for (int i = 0; i < 20; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new BoxLayout(panels[i],BoxLayout.Y_AXIS));
            groups[i]=new ButtonGroup();
            panels[i].setVisible(true);
            panels[i].add(new JLabel(getQuestion()));
            for (int j = 0; j < 4; j++) {
                btn[j] = new JRadioButton(generateQuetion(i));
                panels[i].add(btn[j]);
                groups[i].add(btn[j]);
            }
            Const.ANSWER_ID=0;
            container.add(panels[i]);
        }
        JButton answ = new JButton("Завершить");

       answ.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               doCheckAnswers();
                FinishResult fr = new FinishResult();
               fr.textArea1.setText("asdasdasdasd");
               fr.showFinishResult();
           }
       });


        studentFrame.getContentPane().add(jScrollPane1);
        studentFrame.add(answ,BorderLayout.SOUTH);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(800, 800);
        studentFrame.setVisible(true);
    }

    private String generateQuetion(int questID) throws ClassNotFoundException, SQLException {
        String res = null;
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        PreparedStatement preparedStmt = conn.prepareStatement("SELECT Ans1,Ans2,Ans3,Ans4 FROM QuestionList WHERE id = ?");
        preparedStmt.setInt(1,questions.get(questID));

        res = preparedStmt.executeQuery().getString("Ans"+(Const.ANSWER_ID+1));
        Const.ANSWER_ID+=1;
        return res;
    }

    private String getQuestion() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");


        PreparedStatement findMax = conn.prepareStatement("SELECT MAX(id) as id FROM QuestionList");
        int max = findMax.executeQuery().getInt("id");


        int k = 0;
        while(k !=1) {
            Random rand = new Random();
            int rnd = rand.nextInt(max);
            if (!questions.contains(rnd) && rnd != 0) {
               // System.out.println(rnd);
                questions.add(rnd);
                k++;
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT Question,`Right` FROM QuestionList WHERE id = ?");
                preparedStatement.setInt(1,rnd); //пиздюк
                faq.put(rnd,preparedStatement.executeQuery().getInt("Right"));
                return preparedStatement.executeQuery().getString("Question");
            }
        }

        return null;
    }

    private void doCheckAnswers() {
        //TODO проверка овтетов и вывод конечного резульатат (баллы)
       for (Map.Entry<Integer, Integer> pair : faq.entrySet()) {
           System.out.println(pair.getKey());
       }
    }
}
