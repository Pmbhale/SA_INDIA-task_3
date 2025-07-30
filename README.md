# SA_INDIA-task_3
Realistic Quiz Application With Token Based authentication and Score check with Shuffling of Questions
# 🧠 Java Quiz Application

This is a **Java-based Quiz Application** built using **Swing** for GUI and **MySQL** for backend data storage. The app displays multiple-choice questions one by one with a timer, and allows users to select an option and submit their answers. After finishing, users can view their scores and correct answers.

---

## 🚀 Features

- ✅ **Multiple Choice Questions (MCQs):** Each question has four options, and users can select only one.
- ⏳ **Countdown Timer:** A timer is displayed for each question (e.g., 10 seconds per question).
- ⏱️ **Auto-Submit on Timeout:** If the user doesn’t respond in time, the answer is auto-submitted or skipped.
- 🧮 **Score Calculation:** The app calculates the final score based on correct answers.
- 📊 **View Correct Answers:** After finishing the quiz, users can see which answers were correct.
- 🔀 **Randomized Question Order:** Questions are shuffled randomly each time the quiz starts.
- 💾 **Database Integration:** All questions and options are stored and retrieved from a MySQL database.
- 🎨 **User-Friendly GUI:** A clean and intuitive interface built with Java Swing.
- 🔁 **Quiz Replay Option:** Users can retake the quiz after finishing it.

---

## 🛠️ Technologies Used

- Java (JDK 8+)
- Swing (GUI)
- MySQL (Backend Database)
- JDBC (for DB connectivity)

---

## 📷 Screenshots

> Add screenshots here (optional)\
> Example: `images/quiz-screenshot.png`

---

## 🔧 How to Run

### Prerequisites
- Java JDK 8 or higher
- MySQL Server
- MySQL Connector/J (JDBC driver)

### Steps
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/quiz-app-java.git
    cd quiz-app-java
    ```

2. Import the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).

3. Set up the MySQL database:
    - Create a database (e.g., `quizdb`)
    - Create a `questions` table:
      ```sql
      CREATE TABLE questions (
          id INT PRIMARY KEY AUTO_INCREMENT,
          question TEXT,
          option1 VARCHAR(100),
          option2 VARCHAR(100),
          option3 VARCHAR(100),
          option4 VARCHAR(100),
          correct_option VARCHAR(100)
      );
      ```
    - Insert some quiz data.

4. Update your DB connection settings inside the code:
    ```java
    String url = "jdbc:mysql://localhost:3306/quizdb";
    String username = "root";
    String password = "your_password";
    ```

5. Compile and run the program.

---

## 📁 Project Structure

Quiz_APP/
├── QuizCardUI.java # Main GUI class
├── Question.java # Model class for questions
├── Database.java # Handles DB connectivity and queries
├── README.md
└── ...

**Parth Bhale** Intern at SAINDIA 
📧 Email: parthbhale1234@gmail.com  
🔗 [Linkedin](https://www.linkedin.com/in/parth-bhale-8658222a6/)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
