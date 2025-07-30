package Quiz_APP;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.UUID;

public class TokenGenerator extends JFrame {
    Connection conn;
    JTextField nameField, tokenField,Enrollmentfield;
    String Qry;
   Long Enrollment;
    public TokenGenerator(Connection conn) {
        this.conn = conn;

        setTitle("Generate Student Token");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel nameLbl = new JLabel("Student Name:");
        nameLbl.setBounds(30, 30, 100, 25);
        add(nameLbl);

        JLabel nameLbl1 = new JLabel("Student Enrollment:");
        nameLbl1.setBounds(30, 80, 150, 25);
        add(nameLbl1);

        nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 25);
        add(nameField);

        Enrollmentfield = new JTextField();
        Enrollmentfield.setBounds(150, 80, 200, 25);
        add(Enrollmentfield);

        JButton genBtn = new JButton("Generate Token");
        genBtn.setBounds(120, 120, 150, 30);
        add(genBtn);

        tokenField = new JTextField();
        tokenField.setBounds(80, 150, 240, 30);
        tokenField.setEditable(false);
        add(tokenField);

        genBtn.addActionListener(e -> generate());

        setVisible(true);

    }
     boolean check_E(Long enrollment){
boolean flag=false;         Qry="Select*from students where Enrollment=?";
         try {
             PreparedStatement stmt = conn.prepareStatement(Qry);
             stmt.setLong(1,enrollment);
             ResultSet rs1=stmt.executeQuery();
             if(rs1.next()){
                 JOptionPane.showMessageDialog(this,"User Already exists");
                 flag=false;
             }
             else{
                 flag=true;
             }

         }catch(SQLException e){
             System.out.println(e);
         }
         return flag;
     }
    void generate() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter student name");
            return;
        }


         Enrollment= Long.parseLong(Enrollmentfield.getText());
        if (check_E(Enrollment)){
            String token = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            tokenField.setText(token);
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO students (name,Enrollment, token) VALUES (?, ? ,?)");
            ps.setString(1, name);
            ps.setLong(2, Enrollment);
            ps.setString(3, token);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Token generated & saved!");
        } catch (SQLException e) {
            e.printStackTrace();
        } }
         else {
             tokenField.setText("");
           JOptionPane.showMessageDialog(this,"Unable to generate Token");
         }
    }
}
