package Quiz_APP;

import javax.swing.*;
import java.awt.*;
import java.util.List;



class AnswerViewer extends JFrame {
    public AnswerViewer(List<Question> questions) {
        setTitle("Correct Answers");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(null);
        int y = 10;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String[] opts = {q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()};

            JLabel qLabel = new JLabel((i + 1) + ". " + q.getQuestion());
            qLabel.setBounds(10, y, 550, 20);
            answerPanel.add(qLabel);
            y += 25;

            for (int j = 0; j < 4; j++) {
                String text = (char) ('A' + j) + ". " + opts[j];
                JLabel optLabel = new JLabel(text);
                optLabel.setBounds(30, y, 500, 20);

                if (j + 1 == q.getCorrectOption()) {
                    optLabel.setForeground(Color.GREEN);
                    optLabel.setFont(new Font("Arial", Font.BOLD, 12));
                    optLabel.setText(text + " ✔");
                }

                answerPanel.add(optLabel);
                y += 22;
            }
            y += 10;
        }

        answerPanel.setPreferredSize(new Dimension(550, y));
        JScrollPane scroll = new JScrollPane(answerPanel);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
