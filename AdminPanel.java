package Quiz_APP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame implements ActionListener {

    JTextArea questionField;
    JTextField opt1Field, opt2Field, opt3Field, opt4Field, correctField;
    JButton submitBtn;

    Connection conn;

    public AdminPanel(Connection conn) {
        this.conn=conn;
        setTitle("Quiz Admin Panel");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        // Form fields
        add(new JLabel("Enter Question:"));
        questionField = new JTextArea(3, 20);
        add(new JScrollPane(questionField));

        add(new JLabel("Option 1:"));
        opt1Field = new JTextField();
        add(opt1Field);

        add(new JLabel("Option 2:"));
        opt2Field = new JTextField();
        add(opt2Field);

        add(new JLabel("Option 3:"));
        opt3Field = new JTextField();
        add(opt3Field);

        add(new JLabel("Option 4:"));
        opt4Field = new JTextField();
        add(opt4Field);

        add(new JLabel("Correct Option (1-4):"));
        correctField = new JTextField();
        add(correctField);

        submitBtn = new JButton("Add Question");
        submitBtn.addActionListener(this);
        add(submitBtn);



        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String question = questionField.getText();
        String opt1 = opt1Field.getText();
        String opt2 = opt2Field.getText();
        String opt3 = opt3Field.getText();
        String opt4 = opt4Field.getText();
        int correct = Integer.parseInt(correctField.getText());

        try {
            String query = "INSERT INTO quiz_questions (question, opt1, opt2, opt3, opt4, correct_option) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, question);
            ps.setString(2, opt1);
            ps.setString(3, opt2);
            ps.setString(4, opt3);
            ps.setString(5, opt4);
            ps.setInt(6, correct);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Question added successfully!");
                questionField.setText("");
                opt1Field.setText("");
                opt2Field.setText("");
                opt3Field.setText("");
                opt4Field.setText("");
                correctField.setText("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving question: " + ex.getMessage());
        }
    }


}

