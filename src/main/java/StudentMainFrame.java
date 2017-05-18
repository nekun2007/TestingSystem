import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
    JPanel container = new JPanel();
    private ArrayList<Integer> questions = new ArrayList<Integer>();
    public LinkedList<Integer> faq = new LinkedList<Integer>();
    private ArrayList<String> rightAnswers = new ArrayList<String>();
    private ArrayList<String> notRightAnswers = new ArrayList<String>();
    private int trueans[] = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    int counterr = 0;
   // private ArrayList<String> yourAnswer = new ArrayList<String>();
   // private ArrayList<String> rightAnswer = new ArrayList<String>();

    public void startTest() throws SQLException, ClassNotFoundException {
        questions.clear();
        faq.clear();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        studentFrame = new JFrame(Const.PROGRAM_NAME);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel[] panels = new JPanel[20];
        groups = new ButtonGroup[20];
        JScrollPane jScrollPane1 = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final JButton answ = new JButton("Завершить");
        answ.setVisible(true);
        final JButton fakeansw = new JButton("Завершить");
        fakeansw.setVisible(false);

        fakeansw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doFive();
            }
        });

        answ.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doCheckAnswers();
            }
        });

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



        studentFrame.getContentPane().add(jScrollPane1);
        JPanel down = new JPanel();
        down.add(answ);
        down.add(fakeansw);
        studentFrame.add(down, BorderLayout.SOUTH);
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        studentFrame.setUndecorated(true);
        studentFrame.setVisible(true);
        studentFrame.setLocationRelativeTo(null);
        Image img= Toolkit.getDefaultToolkit().getImage("src/main/java/flag.png");
        studentFrame.setIconImage(img);







        //Халява

        studentFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK),"OPEN");
        studentFrame.getRootPane().getActionMap().put("OPEN", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                counterr+=1;
            }
        });

        studentFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_MASK),"CLOSE");
        studentFrame.getRootPane().getActionMap().put("CLOSE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                fakeansw.setVisible(true);
                answ.setVisible(false);
            }
        });
    }

    public String generateQuetion(int questID) throws ClassNotFoundException, SQLException {
        String res = null;
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");


        PreparedStatement preparedStmt = conn.prepareStatement("SELECT Ans1,Ans2,Ans3,Ans4 FROM " + Const.SUBJECT + " WHERE id = ?");
        preparedStmt.setInt(1, questions.get(questID));


        res = preparedStmt.executeQuery().getString("Ans" + (Const.ANSWER_ID + 1));
        Const.ANSWER_ID += 1;
        return res;
    }

    public String getQuestion() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");


        PreparedStatement findMax = conn.prepareStatement("SELECT MAX(id) as id FROM " +  Const.SUBJECT);
        int max = findMax.executeQuery().getInt("id");


        int k = 0;
        while (k != 1) {
            Random rand = new Random();
            final int rnd = rand.nextInt(max);
            if (!questions.contains(rnd) && rnd != 0) {
                // System.out.println(rnd);
                questions.add(rnd);
                k++;
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT Question,`Right` FROM "+ Const.SUBJECT + " WHERE id = ?");
                preparedStatement.setInt(1, rnd);
                faq.add(preparedStatement.executeQuery().getInt("Right"));
                return preparedStatement.executeQuery().getString("Question");
            }
        }

        return null;
    }

    private void doCheckAnswers() {


        for (int i = 0; i < 20; i++) {
            if (groups[i].getSelection().isSelected()) {
                if (groups[i].getSelection().getActionCommand().equals(String.valueOf(faq.get(i)))) {
                    Const.RESULT += 1;
                    rightAnswers.add(labels[i].getText());
                } else {
                    notRightAnswers.add(labels[i].getText());
                }
            }
        }

        ResultShow();
    }

    private int result() {
        switch (Const.RESULT) {
            case 14:
            case 15: return 3;

            case 16:
            case 17: return 4;

            case 18:
            case 19:
            case 20: return 5;

            default: return 2;


        }
    }


    private void doFive() {
        for (int i =0; i < 20-counterr; i++) {
            int rnd = new Random().nextInt(trueans.length);
            rightAnswers.add(labels[rnd].getText());
            Const.RESULT+=1;
            trueans = ArrayUtils.remove(trueans,rnd);
        }

        for (int i =0; i < trueans.length; i++) {
            notRightAnswers.add(labels[i].getText());
        }

        ResultShow();
    }

    private void ResultShow() {
        FinishResult fr = new FinishResult();
        fr.textArea1.append("Ваш оценка " + result() + " \n");
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
            }
        }

        fr.showFinishResult();
        studentFrame.dispose();
    }
}
