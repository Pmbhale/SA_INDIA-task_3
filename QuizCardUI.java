package Quiz_APP;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.Collections;

public class QuizCardUI extends JFrame {
    private JLabel questionLabel, timerLabel;
    private JRadioButton[] options;
    private ButtonGroup bg;
    private List<Question> questions;
    private int currentIndex = 1;
    private int score = 0;
    private Timer timer;
    private int timeLeft = 15;
    JPanel panel = new JPanel();
    private Connection conn;
    JButton nextBtn1;
    private String token = Login_.token; // Use your token
    int n=0;
    public QuizCardUI() {
        setTitle("Quiz App");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);


        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(50, 30, 500, 300);
        add(panel);
        ImageIcon i1=new ImageIcon(getClass().getResource("bck.png"));
        Image u=i1.getImage().getScaledInstance(600,400,Image.SCALE_DEFAULT);
        ImageIcon j=new ImageIcon(u);
        JLabel kd=new JLabel(j);
        add(kd);
        kd.setBounds(0,0,600,400);
        JProgressBar jf=new JProgressBar(0,100);
        jf.setValue(n);
        jf.setStringPainted(true);
        jf.setBorderPainted(true);

        jf.setBounds(145,270,200,20);

        questionLabel = new JLabel("Question appears here");
        questionLabel.setBounds(20, 10, 460, 40);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(questionLabel);

        options = new JRadioButton[4];
        bg = new ButtonGroup();
        int y = 60;
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(20, y, 460, 30);
            panel.add(options[i]);
            bg.add(options[i]);
            y += 40;
        }
         JPanel p=new JPanel();
        p.setSize(600,800);
        JLabel l=new JLabel("Correct Answers");
        p.setLayout(null);
        p.add(l);
        p.setBounds(500,10,150,20);
        p.setVisible(true);
        add(p);



        timerLabel = new JLabel("Time: 15");
        timerLabel.setBounds(420, 20, 60, 25);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);
        panel.add(timerLabel);
        JButton retake =new JButton("Retake");
        retake.setBounds(20,230,90,30);
        panel.add(retake);
        retake.addActionListener(ae-> {
          JOptionPane.showMessageDialog(this,"Restarting The Quiz");
          nextBtn1.setVisible(false);
          n=0;
          jf.setValue(n);
          score=0;
                currentIndex=1;
                showQuestion();
                checkAnswer();
                resetTimer();


        });
        retake.setBackground(new Color(54, 57, 58));
        retake.setForeground(Color.white);
        JButton nextBtn = new JButton("Next");
        nextBtn.setBounds(380, 230, 90, 30);
        nextBtn.setBackground(new Color(54,57,58));
        nextBtn.setForeground(Color.WHITE);
        panel.add(nextBtn);
         nextBtn1 = new JButton("View Answers");
        nextBtn1.setBounds(170, 230, 150, 30);
        nextBtn1.setBackground(new Color(54,57,58));
        nextBtn1.setForeground(Color.WHITE);
        nextBtn1.addActionListener(e -> {
                 new AnswerViewer(questions);
                 dispose();


        });




        nextBtn.addActionListener(e -> {

            if (timer != null) timer.stop();
            checkAnswer();
            currentIndex++;
            n+=12;
            jf.setValue(n);
            System.out.println(currentIndex);
            System.out.println(questions.size());
            if (currentIndex < questions.size()) {
                showQuestion();
                resetTimer();
            } else {
                nextBtn1.setVisible(true);
                panel.add(nextBtn1);
                panel.add(retake);
                JOptionPane.showMessageDialog(this,"Your Score is "+score);
                endQuiz();
            }
        });

        loadQuestionsFromDB();
        if (!questions.isEmpty()) {
            showQuestion();
            startTimer();
        } else {
            questionLabel.setText("No questions found!");
        }
        panel.add(jf);
        setVisible(true);
    }

    public void  loadQuestionsFromDB() {
        questions = new ArrayList<>();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Sa_Quizapp", "root", "admin123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_questions");

            while (rs.next()) {
                questions.add(new Question(
                        rs.getString("question"),
                        rs.getString("opt1"),
                        rs.getString("opt2"),
                        rs.getString("opt3"),
                        rs.getString("opt4"),
                        rs.getInt("correct_option")
                ));
            }
            java.util.Collections.shuffle(questions);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    void showQuestion() {
        bg.clearSelection();
        Question q = questions.get(currentIndex);
        questionLabel.setText("<html>" + currentIndex+") " + "   " +  q.getQuestion() + "</html>");
        options[0].setText(q.getOption1());
        options[1].setText(q.getOption2());
        options[2].setText(q.getOption3());
        options[3].setText(q.getOption4());
    }



    void checkAnswer() {
        Question q = questions.get(currentIndex);
        int selected = -1;
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                selected = i + 1;
                break;
            }
        }
        if (selected == q.getCorrectOption()) {
            score++;
        }
    }

    void startTimer() {
        timeLeft = 15;
        timerLabel.setText("Time: " + timeLeft);
        timerLabel.setBackground(new Color(139, 191, 221));
        timerLabel.setOpaque(true);
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft <= 0) {
                if (timer != null) timer.stop();
                JOptionPane.showMessageDialog(this, "Time's up!");
                checkAnswer();
                currentIndex++;

                if (currentIndex < questions.size()) {
                    showQuestion();
                    resetTimer();
                } else {
                    endQuiz();
                }
            }
        });
        timer.start();
    }

    void resetTimer() {
        if (timer != null) timer.stop();
        startTimer();
    }

    void endQuiz() {
        if (timer != null) timer.stop();

        // Update score in DB
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE students SET score = " + score + " WHERE token = '" + token + "'");
            System.out.println("✅ Score saved to DB.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

     }

    public static void main(String[] args) {
        new QuizCardUI();
    }
}
