import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

/**
 * Created by Никита on 18.03.2017.
 */
public class StudentMainFrame {
    JFrame studentFrame;
    ButtonGroup groups[] = new ButtonGroup[20];
    JRadioButton btn[] = new JRadioButton[80];
    JLabel[] labels = new JLabel[20];
    private ArrayList<Integer> questions = new ArrayList<Integer>();
    private LinkedList<Integer> faq = new LinkedList<Integer>();
    private ArrayList<String> rightAnswers = new ArrayList<String>();
    private ArrayList<String> notRightAnswers = new ArrayList<String>();
   // private ArrayList<String> yourAnswer = new ArrayList<String>();
   // private ArrayList<String> rightAnswer = new ArrayList<String>();

    public StudentMainFrame() throws SQLException, ClassNotFoundException {
        questions.clear();
        faq.clear();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        studentFrame = new JFrame(Const.PROGRAM_NAME);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel[] panels = new JPanel[20];
        groups = new ButtonGroup[20];
        JScrollPane jScrollPane1 = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        for (int i = 0; i < 20; i++) {
            panels[i] = new JPanel();
            labels[i] = new JLabel((i + 1) + ". " + getQuestion());
            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
            groups[i] = new ButtonGroup();
            panels[i].setVisible(true);
            panels[i].add(labels[i]);
            for (int j = 0; j < 4; j++) {
                btn[j] = new JRadioButton(generateQuetion(i));
                btn[j].setActionCommand(String.valueOf(j + 1));
                panels[i].add(btn[j]);
                groups[i].add(btn[j]);
            }
            JRadioButton hidden = new JRadioButton("hidden", true);
            hidden.setVisible(false);
            groups[i].add(hidden);
            hidden.setActionCommand("0");
            Const.ANSWER_ID = 0;
            container.add(panels[i]);
        }
        JButton answ = new JButton("Завершить");

        answ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doCheckAnswers();
            }
        });


        studentFrame.getContentPane().add(jScrollPane1);
        studentFrame.add(answ, BorderLayout.SOUTH);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        studentFrame.setUndecorated(true);
        //studentFrame.setSize(800, 800);
        studentFrame.setVisible(true);
        studentFrame.setLocationRelativeTo(null);
        Image img= Toolkit.getDefaultToolkit().getImage("src/main/java/flag.png");
        studentFrame.setIconImage(img);

    }

    private String generateQuetion(int questID) throws ClassNotFoundException, SQLException {
        String res = null;
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");

        Statement statement = conn.createStatement();
        statement.execute(String.format("SELECT Ans1,Ans2,Ans3,Ans4 FROM %s WHERE id = %d", mainFrame.tableName, questions.get(questID)));

        /*PreparedStatement preparedStmt = conn.prepareStatement("SELECT Ans1,Ans2,Ans3,Ans4 FROM ? WHERE id = ?");
        preparedStmt.setString(1, mainFrame.tableName);
        preparedStmt.setInt(2, questions.get(questID));*/


        res = statement.executeQuery(String.format("SELECT %s FROM %s WHERE id = %d","Ans" + (Const.ANSWER_ID + 1), mainFrame.tableName, questions.get(questID) )).getString("Ans" + (Const.ANSWER_ID + 1));
        //res = preparedStmt.executeQuery().getString("Ans" + (Const.ANSWER_ID + 1));
        Const.ANSWER_ID += 1;
        return res;
    }

    private String getQuestion() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");


        /*PreparedStatement findMax = conn.prepareStatement(String.format("SELECT MAX(id) as id FROM '%s'", mainFrame.tableName));
        findMax.setString(1, mainFrame.tableName);
        System.out.println(mainFrame.tableName);
        int max = findMax.executeQuery().getInt("id");*/
        Statement findMax = conn.createStatement();
        int max = findMax.executeQuery(String.format("SELECT MAX(id) as id FROM '%s'", mainFrame.tableName)).getInt("id");


        int k = 0;
        while (k != 1) {
            Random rand = new Random();
            final int rnd = rand.nextInt(max);
            if (!questions.contains(rnd) && rnd != 0) {
                // System.out.println(rnd);
                questions.add(rnd);
                k++;
                /*PreparedStatement preparedStatement = conn.prepareStatement("SELECT Question,`Right` FROM '?' WHERE id = ?");
                preparedStatement.setString(1, mainFrame.tableName);
                preparedStatement.setInt(2, rnd);
                faq.add(preparedStatement.executeQuery().getInt("Right"));
                return preparedStatement.executeQuery().getString("Question");*/
                Statement stmnt = conn.createStatement();
                stmnt.execute(String.format("SELECT Question,`Right` FROM '%s' WHERE id = %d",mainFrame.tableName, rnd ));
                faq.add(stmnt.executeQuery(String.format("SELECT`Right` FROM '%s' WHERE id = %d",mainFrame.tableName, rnd )).getInt("Right"));
                return stmnt.executeQuery(String.format("SELECT Question FROM '%s' WHERE id = %d",mainFrame.tableName, rnd )).getString("Question");
            }
        }

        return null;
    }

    private void doCheckAnswers() {
        FinishResult fr = new FinishResult();

        for (int i = 0; i < 20; i++) {
            if (groups[i].getSelection().isSelected()) {
                if (groups[i].getSelection().getActionCommand().equals(String.valueOf(faq.get(i)))) {
                    Const.RESULT += 1;
                    rightAnswers.add(labels[i].getText());
                } else {
                    notRightAnswers.add(labels[i].getText());
                    //yourAnswer.add(groups[i].getSelection().getActionCommand());
                    //rightAnswer.add(String.valueOf(faq.get(i)));
                }
            }
        }

        fr.textArea1.append("Ваш результат " + Const.RESULT + " из 20\n");
        System.out.println(rightAnswers.size());
        if (rightAnswers.size() > 0) {
            fr.textArea1.append("\n");
            fr.textArea1.append("Вы ответили правильно на следующие вопросы: \n");
            for (int i = 0; i < rightAnswers.size(); i++) {
                fr.textArea1.append(rightAnswers.get(i) + "\n");
            }
        }

        if (notRightAnswers.size() != 0) {
            fr.textArea1.append("\n");
            fr.textArea1.append("Вам не удалось ответить на следующие вопросы: \n");
            for (int i = 0; i < notRightAnswers.size(); i++) {
                fr.textArea1.append(notRightAnswers.get(i) + "\n");
                //fr.textArea1.append("Вы ответили: \n" + "   " + yourAnswer.get(i) + "\n");
               // fr.textArea1.append("Верный ответ: \n" + "   " + yourAnswer.get(i) + "\n");
            }
        }

        fr.showFinishResult();
        studentFrame.dispose();


    }
}
