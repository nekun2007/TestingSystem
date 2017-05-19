import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by Иван on 14.03.2017.
 */
public class Conn {
    public Connection conn;
    public Statement statmt;
    public ResultSet resSet;
    static int max = 0;
    static int flag = 0;

    public Conn() {
    }

    /**
     * Функция подключения к базе данных
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        System.out.println("База Подключена!");
    }

    /**
     * Функция создания таблицы в базе данных
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void CreateDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        flag = 1;
        //statmt.execute("CREATE TABLE if not exists 'Questions' ('id' INTEGER PRIMARY KEY, 'Question' text, 'Ans1' text, 'Ans2' text, 'Ans3' text, 'Ans4' text, 'Right' INTEGER);");
        System.out.println("Таблица создана или уже существует.");
    }

    /**
     * Добавление в базу данных вопрос
     * @param que - переменная отвечающая за текст вопроса
     * @param ans1 - 1 вариант ответа
     * @param ans2 - 2 вариант ответа
     * @param ans3 - 3 вариант ответа
     * @param ans4 - 4 вариант ответа
     * @param right - правильный вариант ответа
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void WriteDB(String que, String ans1, String ans2, String ans3, String ans4, int right) throws SQLException, IOException, ClassNotFoundException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        System.out.println("База Подключена!");
        statmt = conn.createStatement();
        max = 1;


            if(max == 0 && flag == 0) {
               // System.out.println("Таблица пуста");
            } else {
                max = statmt.executeQuery(String.format("SELECT MAX(id) AS id FROM '%s'", Const.SUBJECT)).getInt("id");
            }
            statmt.execute(String.format("INSERT INTO '%s' ('id', 'Question', 'Ans1', 'Ans2', 'Ans3', 'Ans4', 'Right') VALUES ('%d', '%s', '%s','%s','%s','%s', %d); ", Const.SUBJECT, max + 1, que, ans1, ans2, ans3, ans4, right));
            System.out.println("Вопрос добавлен");


    }

    /**
     * Удаление вопроса из базы и сортировка номера вопроса
     * @param id - номер вопроса в базе данных
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void DeleteQuest(int id) throws SQLException, ClassNotFoundException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery(String.format("SELECT * FROM '%s'", Const.SUBJECT));
        statmt.executeUpdate(String.format("", new Object[0]));
        statmt.execute(String.format("DELETE FROM `%s` WHERE `id`='%d'", Const.SUBJECT,id));
        int maxId = statmt.executeQuery(String.format("SELECT MAX(id) AS id FROM '%s';", Const.SUBJECT)).getInt("id");

        for(int i = id + 1; i <= maxId; ++i) {
            statmt.executeUpdate(String.format("UPDATE '%s' SET id = %d WHERE id=%d",Const.SUBJECT, i - 1, i));
        }
        System.out.println("Row is deleted");
        resSet.close();
    }
}
