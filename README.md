# Adaptive AI Study Assistant for Personalized Learning

**BloomLearn** is a beginner-friendly Java full-stack college project. It helps students practise topics, take MCQ quizzes, track accuracy, and receive adaptive study recommendations.

## Features

- Student registration and login (passwords are BCrypt-hashed).
- Four seeded subjects: Java Programming, Data Structures, DBMS, and Operating Systems.
- Topic selection and multiple-choice quizzes.
- Automatic scoring with answer explanations.
- Saved quiz history and topic-wise performance.
- Rule-based adaptive learning engine that changes the next difficulty.
- Dashboard with average score, completed quizzes, strongest/weakest topic and recent activity.
- Personalised recommendations with priority labels.
- Profile editing.
- Responsive frontend with soft beige, lavender, pink and navy styling.
- MySQL support plus a file-based H2 mode for the quickest first run.
- Basic unit tests for adaptive decision rules.

The adaptive engine is a transparent, rule-based intelligent scoring mechanism, not a generative AI model. It uses accuracy, the amount of practice evidence and the latest quiz result to assign BEGINNER, INTERMEDIATE or ADVANCED levels. No paid AI API is required.

## Technologies

- Java 21, Spring Boot 3, Maven
- Spring Web REST API, Spring Data JPA / Hibernate, Jakarta Validation
- H2 for quick local demonstration; MySQL profile for MySQL
- HTML5, CSS3, JavaScript, Bootstrap 5

## Folder structure

```text
Adaptive-AI-Study-Assistant/
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/java/edu/studyassistant/
│       │   ├── config/          # Seed data
│       │   ├── controller/      # REST API
│       │   ├── dto/             # Request/response models
│       │   ├── model/           # JPA entities
│       │   ├── repository/      # Database access
│       │   └── service/         # Business rules and adaptive engine
│       ├── main/resources/      # Application configuration
│       └── test/                # Adaptive engine tests
├── frontend/
│   ├── index.html
│   ├── css/styles.css
│   └── js/app.js
├── database/
│   ├── schema.sql
│   └── sample_data.sql
└── README.md
```

## Requirements

Install:
1. **JDK 21** (check with `java -version`).
2. **Apache Maven 3.9+** (check with `mvn -version`). Maven downloads project dependencies on the first build.
3. **Visual Studio Code**.
4. VS Code extensions: **Extension Pack for Java**, **Spring Boot Extension Pack**, and **Live Server**.
5. An internet connection for Maven dependency downloads and Bootstrap CDN styling.

MySQL is optional for the fastest demo. The default configuration uses a local H2 database file, so no database server is required to try the application. MySQL is supported via the `mysql` Spring profile and SQL scripts below.

## Fastest run in VS Code (recommended first)

1. Extract the ZIP.
2. Open the `Adaptive-AI-Study-Assistant` folder in VS Code.
3. Open **Terminal → New Terminal**, then run:

   ```bash
   cd backend
   mvn spring-boot:run
   ```

4. Wait until Spring Boot says it has started on port 8080. The first start downloads dependencies and may take a few minutes. It creates a local H2 database file under `backend/data/` and seeds the subjects, topics, questions and demo account.
5. Open a second VS Code terminal:

   ```bash
   cd frontend
   ```

   In VS Code Explorer, right-click `frontend/index.html` and select **Open with Live Server**. Alternatively, open the `index.html` file directly, although Live Server is more reliable.
6. Browse to the Live Server URL shown by VS Code (usually `http://127.0.0.1:5500`).
7. Log in with the demo account below or create your own account.

**Demo login**
- Email: `demo@study.ai`
- Password: `Study123!`

Backend API base URL: `http://localhost:8080/api`  
H2 console (optional): `http://localhost:8080/h2-console`  
For H2 console use JDBC URL `jdbc:h2:file:./data/studyassistant`, username `sa`, and a blank password.

## Use MySQL instead

1. Start MySQL Server.
2. Open MySQL Workbench or a MySQL terminal and execute `database/schema.sql`.
3. Optionally execute `database/sample_data.sql` to pre-create subjects and topics. The application will add questions and the demo account on startup if they are missing.
4. In the VS Code terminal, set your credentials (Windows PowerShell):

   ```powershell
   $env:DB_USERNAME="root"
   $env:DB_PASSWORD="your_mysql_password"
   cd backend
   mvn spring-boot:run "-Dspring-boot.run.profiles=mysql"
   ```

   On macOS/Linux:

   ```bash
   export DB_USERNAME=root
   export DB_PASSWORD='your_mysql_password'
   cd backend
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

   The URL in `application-mysql.properties` creates the database if it does not exist. The schema script is also provided for inspection and manual setup. Change the username/password environment variables if your local MySQL account differs.

The default profile uses H2; the `mysql` profile switches the datasource to MySQL. Do not run both backends at the same time on port 8080.

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/students/register` | Register student |
| POST | `/api/students/login` | Login |
| GET | `/api/subjects` | List subjects |
| GET | `/api/subjects/{id}/topics` | List subject topics |
| GET | `/api/topics/{id}/questions?studentId={id}` | Get adaptive quiz questions |
| POST | `/api/quizzes/submit` | Score quiz and update learning data |
| GET | `/api/students/{id}/performance` | Topic-wise performance |
| GET | `/api/students/{id}/recommendations` | Personal recommendations |
| GET | `/api/students/{id}/dashboard` | Dashboard data |
| PUT | `/api/students/{id}` | Update profile |

## Adaptive algorithm

1. Every submitted answer is checked against the stored answer key.
2. Total correct answers, attempts and accuracy are updated for the selected topic.
3. With fewer than four attempted questions, the engine blends current accuracy with a neutral 50% baseline to avoid making a strong decision from very little evidence.
4. With at least four attempts, the score is blended with the latest quiz percentage to incorporate recent performance.
5. Below 40% maps to **BEGINNER** and high-priority fundamentals/revision; 40% to below 72% maps to **INTERMEDIATE** and concept reinforcement; 72% or higher maps to **ADVANCED** and challenge practice.
6. The next quiz first tries questions at the student's current level, and falls back to the topic's available questions if there are fewer than five at that level.
7. Recommendations explain the next step and include HIGH, MEDIUM or LOW priority.

This is an educational rule-based adaptive system; it does not claim to be a trained machine-learning model.

## OOP concepts used

- **Encapsulation:** entity fields are private and exposed with getters/setters.
- **Abstraction:** repository and service APIs separate what a component does from implementation details.
- **Interfaces:** Spring Data repository interfaces and their generated implementations; service classes keep business logic separate from controllers.
- **Inheritance:** JPA entities inherit common persistence behaviour from Spring Data/JPA infrastructure. The project avoids inventing an artificial `User → Student` hierarchy just to tick a box.
- **Polymorphism:** repository interfaces are used through their declared types and framework-provided implementations.
- **Collections:** `List`, `Map`, `Set`, streams and sorting are used for quiz answers, dashboard data and performance analysis.
- **Exception handling:** REST errors return a clear JSON `error` message and invalid input does not produce a successful response.
- **Validation:** registration/login and quiz request DTOs use Jakarta validation constraints.

## Testing

From the `backend` folder:

```bash
mvn test
```

The included tests check beginner/intermediate/advanced level decisions and recommendation priorities.

## Troubleshooting

- **`mvn` is not recognized:** install Maven and add its `bin` folder to PATH, then reopen VS Code.
- **`java` is not recognized or version is below 21:** install JDK 21 and configure `JAVA_HOME`/PATH.
- **Port 8080 is busy:** stop the other process using port 8080, or change `server.port` in `application.properties`.
- **Frontend says Failed to fetch:** make sure the backend is running at `http://localhost:8080`; check the browser console for details.
- **MySQL access denied:** verify `DB_USERNAME` and `DB_PASSWORD`; the default H2 mode does not need MySQL.
- **Demo login fails after changing databases:** the H2 and MySQL databases keep separate users; use the demo credentials for the database currently selected or register a new user.

## Future enhancements

Possible future work includes more questions per difficulty and topic, timed quizzes, learning streaks, stronger session/token authentication, exporting progress reports, and a trained recommendation model evaluated against real learning data.
