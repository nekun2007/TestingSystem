import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Никита on 18.03.2017.
 */
public class TeacherMainFrame extends conn {
    private JTable table1;
    private JButton doAddNewQuest;
    private JButton doRemoveQuest;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private DefaultTableModel defaultTableModel;
    private conn ConnObj;

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public TeacherMainFrame() throws SQLException, ClassNotFoundException {
        ConnObj = new conn();
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

                JFrame DashboardFrame = new JFrame(Const.PROGRAM_NAME);
                DashboardFrame.add(panel1);
                DashboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                                Object right_answ=JOptionPane.showInputDialog(null,"Введите вопрос", "Добавление правильного ответа",JOptionPane.PLAIN_MESSAGE, null,answers.toArray(),answers.get(0));
                                try {
                                    new conn().WriteDB(question,ans1,ans2,ans3,ans4,answers.indexOf(right_answ)+1);
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





    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private DefaultTableModel Update_table() throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");

        preparedStatement = connection.prepareStatement("SELECT * FROM QuestionList");
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
}
