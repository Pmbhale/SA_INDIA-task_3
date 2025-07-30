package Quiz_APP;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewDeleteQuestions extends JFrame {
    Connection conn;
    JTable table;
    DefaultTableModel model;

    public ViewDeleteQuestions(Connection conn) {
        this.conn = conn;

        setTitle("View/Delete Questions");
        setSize(800, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Question");
        model.addColumn("Option 1");
        model.addColumn("Option 2");
        model.addColumn("Option 3");
        model.addColumn("Option 4");
        model.addColumn("Correct");

        loadQuestions();

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);

        JButton deleteBtn = new JButton("🗑️ Delete Selected");
        deleteBtn.addActionListener(e -> deleteQuestion());
        add(deleteBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    void loadQuestions() {
        try {
            model.setRowCount(0); // clear table
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_questions");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("question"),
                        rs.getString("opt1"),
                        rs.getString("opt2"),
                        rs.getString("opt3"),
                        rs.getString("opt4"),
                        rs.getInt("correct_option")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void deleteQuestion() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a question to delete.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM quiz_questions WHERE id = ?");
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Deleted successfully.");
                loadQuestions();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

