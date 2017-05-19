import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Никита on 18.03.2017.
 */
public class TeacherMainFrame extends Conn {
    private JTable table1;
    private JButton doAddNewQuest;
    private JButton doRemoveQuest;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JButton doChangeQuestion;
    private DefaultTableModel defaultTableModel;
    private Conn ConnObj;
    JFrame DashboardFrame = new JFrame(Const.PROGRAM_NAME);

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public TeacherMainFrame() throws SQLException, ClassNotFoundException {
        ConnObj = new Conn();
        ConnObj.Conn();
        ConnObj.CreateDB();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    table1.setModel(Update_table());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                doRemoveQuest.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            ConnObj.DeleteQuest(Integer.parseInt(defaultTableModel.getValueAt(table1.getSelectedRow(), 0).toString()));
                            JOptionPane.showMessageDialog(null,"Вопрос удален успешно", "Операция выполнена",JOptionPane.INFORMATION_MESSAGE);
                            table1.setModel(Update_table());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }


                    }
                });


                DashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                DashboardFrame.add(panel1);
                DashboardFrame.setSize(800, 800);
                DashboardFrame.setVisible(true);
                DashboardFrame.setLocationRelativeTo(null);
                Image img= Toolkit.getDefaultToolkit().getImage("src/main/java/flag.png");
                DashboardFrame.setIconImage(img);

                doAddNewQuest.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String question = JOptionPane.showInputDialog(null,"Введите вопрос", "Добавление нового вопроса",JOptionPane.PLAIN_MESSAGE);
                        ArrayList<String> answers = new ArrayList<String>();
                        if (question.length() >0 ) {
                            String ans1=JOptionPane.showInputDialog(null,"Введите ответ", "Добавление ответа 1",JOptionPane.PLAIN_MESSAGE);
                            answers.add(ans1);
                            String ans2=JOptionPane.showInputDialog(null,"Введите ответ", "Добавление ответа 2",JOptionPane.PLAIN_MESSAGE);
                            answers.add(ans2);
                            String ans3=JOptionPane.showInputDialog(null,"Введите ответ", "Добавление ответа 3",JOptionPane.PLAIN_MESSAGE);
                            answers.add(ans3);
                            String ans4=JOptionPane.showInputDialog(null,"Введите ответ", "Добавление ответа 4",JOptionPane.PLAIN_MESSAGE);
                            answers.add(ans4);

                            if ((ans1.length() + ans2.length() + ans3.length() + ans4.length())>0){
                                Object right_answ=JOptionPane.showInputDialog(null,"Выберите правильный ответ", "Добавление правильного ответа",JOptionPane.PLAIN_MESSAGE, null,answers.toArray(),answers.get(0));

                                try {
                                    new Conn().WriteDB(question,ans1,ans2,ans3,ans4,answers.indexOf(right_answ)+1);
                                    table1.setModel(Update_table());
                                    JOptionPane.showMessageDialog(null,"Новый вопрос добавлен успешно", "Операция выполнена",JOptionPane.INFORMATION_MESSAGE);
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                } catch (ClassNotFoundException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }

                    }
                });
            }

        });
        doChangeQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangeQuestion changeQuestion = new ChangeQuestion();
                changeQuestion.id = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(),0).toString());
                changeQuestion.questionField.setText(table1.getValueAt(table1.getSelectedRow(),1).toString());
                changeQuestion.answerFirstField.setText(table1.getValueAt(table1.getSelectedRow(),2).toString());
                changeQuestion.answerSecondField.setText(table1.getValueAt(table1.getSelectedRow(),3).toString());
                changeQuestion.answerThirdField.setText(table1.getValueAt(table1.getSelectedRow(),4).toString());
                changeQuestion.answerFourthField.setText(table1.getValueAt(table1.getSelectedRow(),5).toString());
                changeQuestion.rightAnswerBox.setSelectedIndex(Integer.parseInt(table1.getValueAt(table1.getSelectedRow(),6).toString())-1);
                DashboardFrame.dispose();
            }
        });

        DashboardFrame.setJMenuBar(MainMenu());

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Функция отвечающая за изменение данных в локальной таблице
     * @return возврщает массив элементов для таблицы
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private DefaultTableModel Update_table() throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        preparedStatement = connection.prepareStatement("SELECT * FROM " + Const.SUBJECT);
        resultSet = preparedStatement.executeQuery();

        defaultTableModel = new DefaultTableModel();
        //Получаем и устанавливаем столбцы
        try {
            final ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            final String[] colNames = new String[resultSetMetaData.getColumnCount()];
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                colNames[i] = resultSetMetaData.getColumnName(i + 1);
            }
            defaultTableModel.setColumnIdentifiers(colNames);

            //Заполняем таблицу содержимым
            while (resultSet.next()) {
                String[] rowData = new String[resultSetMetaData.getColumnCount()];
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    rowData[i] = resultSet.getString(i + 1);
                }
                defaultTableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultTableModel;
    }

    /**
     * Пришлось делать меню. Класс отвечающий за меню с функционалом
     * @return меню
     */
    private JMenuBar MainMenu() {
        JMenuBar bar  = new JMenuBar();

        JMenu fileSec = new JMenu("Файл");
        JMenu testSec = new JMenu("Тест");

        JMenuItem exitItem = new JMenuItem("Выход");
        JMenuItem printTest = new JMenuItem("Печатный тест");

        fileSec.add(exitItem);
        testSec.add(printTest);

        printTest.addActionListener(new printTest());

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        bar.add(fileSec);
        bar.add(testSec);

        return bar;
    }
}
