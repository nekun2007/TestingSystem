import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Никита on 18.03.2017.
 */
public class Db_Test {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:Questions.s3db");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Questions (id, Question, Ans1, Ans2, Ans3, Ans4, Right) VALUES (?,?,?,?,?,?,?)");

        for(int i=10; i < 25; i++) {
            preparedStatement.setInt(1,i);
            preparedStatement.setString(2,"12354");
            preparedStatement.setString(3,"12354");
            preparedStatement.setString(4,"12354");
            preparedStatement.setString(5,"12354");
            preparedStatement.setString(6,"12354");
            preparedStatement.setInt(7,1);
            preparedStatement.execute();
        }
    }
}
