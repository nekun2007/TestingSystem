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
public class conn {
    public Connection conn;
    public Statement statmt;
    public ResultSet resSet;
    static int max = 0;
    static int flag = 0;

    public conn() {
    }

    public void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        System.out.println("База Подключена!");
    }

    public void CreateDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        flag = 1;
        statmt.execute("CREATE TABLE if not exists 'QuestionList' ('id' INTEGER PRIMARY KEY, 'Question' text, 'Ans1' text, 'Ans2' text, 'Ans3' text, 'Ans4' text, 'Right' INTEGER);");
        System.out.println("Таблица создана или уже существует.");
    }

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
                max = statmt.executeQuery("SELECT MAX(id) AS id FROM 'QuestionList'").getInt("id");
            }
            statmt.execute(String.format("INSERT INTO 'QuestionList' ('id', 'Question', 'Ans1', 'Ans2', 'Ans3', 'Ans4', 'Right') VALUES ('%d', '%s', '%s','%s','%s','%s', %d); ", max + 1, que, ans1, ans2, ans3, ans4, right));
            System.out.println("Вопрос добавлен");


    }

    public void  ReadDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM QuestionList");

        while(resSet.next()) {
            int id = resSet.getInt("id");
            String question = resSet.getString("Question");
            String Ans1 = resSet.getString("Ans1");
            String Ans2 = resSet.getString("Ans2");
            String Ans3 = resSet.getString("Ans3");
            String Ans4 = resSet.getString("Ans4");
            System.out.print(id + ". ");
            System.out.println(question);
            System.out.println(" а. " + Ans1);
            System.out.println(" б. " + Ans2);
            System.out.println(" в. " + Ans3);
            System.out.println(" г. " + Ans4);
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    public void DeleteQuest(int id) throws SQLException, ClassNotFoundException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        statmt = conn.createStatement();
        resSet = statmt.executeQuery("SELECT * FROM QuestionList");
        statmt.executeUpdate(String.format("", new Object[0]));
        statmt.execute(String.format("DELETE FROM `QuestionList` WHERE `id`='%d'", id));
        int maxId = statmt.executeQuery("SELECT MAX(id) AS id FROM QuestionList;").getInt("id");

        for(int i = id + 1; i <= maxId; ++i) {
            statmt.executeUpdate(String.format("UPDATE QuestionList SET id = %d WHERE id=%d", i - 1, i));
        }
        System.out.println("Row is deleted");
        resSet.close();
    }

    public void CloseDB() throws ClassNotFoundException, SQLException {
        statmt.close();
        resSet.close();
        conn.close();
        System.out.println("Соединения закрыты");
    }
}
