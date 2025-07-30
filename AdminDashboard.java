package Quiz_APP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public class AdminDashboard extends JFrame {

    Connection conn;

    public AdminDashboard(Connection connection) {
        this.conn = connection;

        setTitle("Admin Dashboard");
        setSize(500, 400);
        setLayout(new GridLayout(5, 1, 10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Welcome to Admin Panel", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        JButton addQ = new JButton("➕ Add New Question");
        JButton viewQ = new JButton("📋 View/Delete Questions");
        JButton genToken = new JButton("🎫 Generate Student Token");
        JButton trackScore = new JButton("📊 Track Student Scores");

        add(addQ);
        add(viewQ);
        add(genToken);
        add(trackScore);

        // Action Listeners
       addQ.addActionListener(e -> new AdminPanel(conn));
        viewQ.addActionListener(e -> new ViewDeleteQuestions(conn));
        genToken.addActionListener(e -> new TokenGenerator(conn));
        trackScore.addActionListener(e -> new TrackScores(conn));

        setVisible(true);
    }
}

