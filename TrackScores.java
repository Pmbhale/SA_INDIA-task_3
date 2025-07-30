package Quiz_APP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TrackScores extends JFrame {
    Connection conn;
    JTable table;
    DefaultTableModel model;

    public TrackScores(Connection conn) {
        this.conn = conn;

        setTitle("Student Scores");
        setSize(500, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Token");
        model.addColumn("Score");

        loadScores();

        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    void loadScores() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("token"),
                        rs.getInt("score")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
