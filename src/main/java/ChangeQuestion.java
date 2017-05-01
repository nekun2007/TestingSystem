import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Иван on 19.04.2017.
 */
public class ChangeQuestion {
    private JPanel panel;
    private JButton Accept;
    private JButton Cancel;
    public JTextField questionField;
    public JTextField answerFirstField;
    public JTextField answerSecondField;
    public JTextField answerThirdField;
    public JTextField answerFourthField;
    public JComboBox rightAnswerBox;
    public int id;

    public ChangeQuestion(){
        final JFrame changeQuestion = new JFrame(Const.PROGRAM_NAME+" ChangeAnswer");
        changeQuestion.add(panel);
        changeQuestion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changeQuestion.setSize(800, 800);
        changeQuestion.setVisible(true);
        rightAnswerBox.addItem("1");
        rightAnswerBox.addItem("2");
        rightAnswerBox.addItem("3");
        rightAnswerBox.addItem("4");
        Accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    applyingChanges();
                    JOptionPane.showMessageDialog(null,"Вопрос изменен!");
                    changeQuestion.dispose();
                    TeacherMainFrame teacherMainFrame = new TeacherMainFrame();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new TeacherMainFrame();
                    changeQuestion.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    private void applyingChanges() throws SQLException {
        Connection connection = DriverManager.getConnection(Const.LINK);
       /* PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ? SET Question=? , Ans1=?, Ans2=?, Ans3=?, Ans4=?, `Right`=? WHERE id=?");
        preparedStatement.setString(1, mainFrame.tableName);
        preparedStatement.setString(2,questionField.getText());
        preparedStatement.setString(3,answerFirstField.getText());
        preparedStatement.setString(4,answerSecondField.getText());
        preparedStatement.setString(5,answerThirdField.getText());
        preparedStatement.setString(6,answerFourthField.getText());
        preparedStatement.setInt(7,rightAnswerBox.getSelectedIndex()+1);
        preparedStatement.setInt(8,id);
        preparedStatement.executeUpdate();*/
        Statement statement = connection.createStatement();
        statement.executeUpdate(String.format("UPDATE %s SET Question=%s , Ans1=%s, Ans2=%s, Ans3=%s, Ans4=%s, `Right`=%d WHERE id=%d",
                mainFrame.tableName,
                questionField.getText(),
                answerFirstField.getText(),
                answerSecondField.getText(),
                answerThirdField.getText(),
                answerFourthField.getText(),
                rightAnswerBox.getSelectedIndex()+1,
                id) b
        );



    }


}
