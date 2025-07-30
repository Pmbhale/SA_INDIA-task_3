package Quiz_APP;
import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

class Student {
    Connection con;
    String User = "root";
    String Pass = "admin123";
    String Url = "jdbc:mysql://localhost:3306/Sa_Quizapp";
    String Query="SELEct * From Students WHere Enrollment=? and Token=?";
    String Enrollment;
    String Token;

    Student() {
        System.out.println("Welcome Student To Quiz App.....");
        System.out.println("Enter Credentials...");
        System.out.println("Enter Enrollment-:");
        Scanner sc1=new Scanner(System.in);
        Enrollment=sc1.next();
        Scanner sc2=new Scanner(System.in);
        System.out.println("Enter Token-:");
        Token=sc2.nextLine();
        try {
            con = DriverManager.getConnection(Url, User, Pass);
            PreparedStatement stmt=con.prepareStatement(Query);
            stmt.setString(1,Enrollment);
            stmt.setString(2,Token);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                System.out.println("Welcome Student.....");
            }else{
                System.out.println("Invalid Credentials Entered...");
            }

        } catch (SQLException e) {
            System.out.println(e);

        }
    }
}
public class Main{
    public static void main(String[]args){
        new Student();
    }

}


