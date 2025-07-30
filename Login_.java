package Quiz_APP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login_ extends JFrame {

    JTextField enrollmentField;
    JTextField tokenField;
    JButton loginButton;
    JCheckBox termsCheckBox;
    JLabel statusLabel;
    static String token;
    public Login_() {
        setTitle("Student Login - Quiz App");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245)); // soft gray
        ImageIcon p=new ImageIcon(getClass().getResource("bvg.png"));
        Image g=p.getImage().getScaledInstance(1000,650,Image.SCALE_DEFAULT);
        ImageIcon gh=new ImageIcon(g);
        JLabel fd=new JLabel(gh);

        fd.setBounds(0,0,1000,650);
        // 📦 Main login panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(150, 80, 700, 450);  // bigger and centered
        add(panel);

        JLabel titleLabel = new JLabel("Quiz Login Portal");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setBounds(240, 20, 300, 40);
        panel.add(titleLabel);

        // 📄 Enrollment field
        JLabel enrollmentLabel = new JLabel("Enrollment No:");
        enrollmentLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        enrollmentLabel.setBounds(100, 90, 150, 30);
        panel.add(enrollmentLabel);

        enrollmentField = new JTextField();
        enrollmentField.setBounds(260, 90, 300, 30);
        panel.add(enrollmentField);

        // 🔐 Token field
        JLabel tokenLabel = new JLabel("Token No:");
        tokenLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        tokenLabel.setBounds(100, 140, 150, 30);
        panel.add(tokenLabel);

        tokenField = new JTextField();
        tokenField.setBounds(260, 140, 300, 30);
        panel.add(tokenField);

        // ✅ Terms checkbox
        termsCheckBox = new JCheckBox("I accept the Terms and Conditions.");
        termsCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        termsCheckBox.setBackground(Color.WHITE);
        termsCheckBox.setBounds(100, 190, 300, 30);
        panel.add(termsCheckBox);

        // 🚪 Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(270, 240, 150, 40);
        loginButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setEnabled(false); // Initially disabled
        panel.add(loginButton);

        // ⚠️ Status message
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setForeground(Color.RED);
        statusLabel.setBounds(100, 290, 500, 25);
        panel.add(statusLabel);

        // 📜 Rules visible on panel
        JTextArea rulesArea = new JTextArea(
                """
                🔔 Rules:
                1. The quiz contains multiple choice questions.
                2. Each question has a 15-second timer.
                3. No going back to previous questions.
                4. Don't refresh or close the window during the quiz.
                5. Enter Correct Token Number provided by your admin .
                """
        );
        rulesArea.setEditable(false);
        rulesArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rulesArea.setBackground(new Color(255, 255, 255));
        rulesArea.setBounds(100, 320, 500, 130);
        rulesArea.setBorder(BorderFactory.createTitledBorder("Quiz Rules"));
        panel.add(rulesArea);

        // ✅ Enable login only after checkbox
        termsCheckBox.addActionListener(e -> {
            loginButton.setEnabled(termsCheckBox.isSelected());
        });

        // 🔐 Login click logic
        loginButton.addActionListener(e -> checkLogin());
        add(fd);
        setVisible(true);
    }

    void checkLogin() {
        String enrollment = enrollmentField.getText().trim();
        token = tokenField.getText().trim();

        if (enrollment.isEmpty() || token.isEmpty()) {
            statusLabel.setText("Please enter both Enrollment and Token.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Sa_Quizapp", "root", "admin123");

            String query = "SELECT * FROM students WHERE enrollment = ? AND token = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, enrollment);
            pst.setString(2, token);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                statusLabel.setForeground(new Color(0, 128, 0)); // green
                statusLabel.setText("Login Successful!");

                dispose(); // close login
                new QuizCardUI(); // launch quiz
            } else {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("Invalid credentials. Try again.");
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            statusLabel.setText("Database error.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login_::new);
    }
}
