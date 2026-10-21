package edu.studyassistant.config;

import edu.studyassistant.model.*;
import edu.studyassistant.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(SubjectRepository subjects, TopicRepository topics,
                           QuestionRepository questions, StudentRepository students) {
        return args -> {
            String[][] subjectData = {
                {"Java Programming", "Core Java and object-oriented programming"},
                {"Data Structures", "Essential data structures and algorithms"},
                {"DBMS", "Relational databases and SQL"},
                {"Operating Systems", "Processes, memory and operating-system concepts"}
            };
            Subject[] savedSubjects = new Subject[subjectData.length];
            for (int i = 0; i < subjectData.length; i++) {
                String name = subjectData[i][0];
                String description = subjectData[i][1];
                savedSubjects[i] = subjects.findAll().stream()
                    .filter(s -> s.getName().equals(name)).findFirst()
                    .orElseGet(() -> subjects.save(new Subject(name, description)));
            }

            String[][] topicNames = {
                {"OOP", "Inheritance", "Exception Handling"},
                {"Arrays", "Stacks and Queues", "Searching"},
                {"SQL Basics", "Normalization", "Transactions"},
                {"Processes and Threads", "Memory Management", "Scheduling"}
            };
            String[][] descriptions = {
                {"Classes, objects, encapsulation and polymorphism", "Extending classes and reusing behaviour", "Handling runtime errors safely"},
                {"Array operations and complexity", "LIFO and FIFO data structures", "Finding items efficiently"},
                {"Queries, filtering and joins", "Reducing redundancy with normal forms", "ACID properties and concurrent updates"},
                {"Process states and thread basics", "Paging and virtual memory", "CPU scheduling approaches"}
            };
            String[][][] questionSets = {
                {
                    {"Which keyword creates an object in Java?", "new", "class", "this", "void", "A", "BEGINNER", "The new operator creates an object."},
                    {"What does encapsulation combine?", "Data and methods", "Only loops", "Only packages", "Only comments", "A", "INTERMEDIATE", "Encapsulation keeps data and related methods together."},
                    {"Which principle allows one interface to have many implementations?", "Compilation", "Polymorphism", "Casting", "Iteration", "B", "ADVANCED", "Polymorphism allows different implementations through a common type."}
                },
                {
                    {"Array indices in Java start at?", "0", "1", "-1", "Depends on value", "A", "BEGINNER", "Java arrays use zero-based indexing."},
                    {"Which structure follows LIFO?", "Queue", "Stack", "Tree", "Graph", "B", "INTERMEDIATE", "A stack removes the most recently added item first."},
                    {"Binary search requires the data to be?", "Random", "Sorted", "Duplicated", "Reversed only", "B", "ADVANCED", "Binary search halves the search space in sorted data."}
                },
                {
                    {"Which SQL command reads rows?", "SELECT", "INSERT", "DROP", "ALTER", "A", "BEGINNER", "SELECT retrieves rows from a table."},
                    {"First normal form requires each field to contain?", "Multiple lists", "Atomic values", "Only numbers", "A primary key twice", "B", "INTERMEDIATE", "1NF requires atomic values and no repeating groups."},
                    {"Which ACID property means all-or-nothing?", "Consistency", "Isolation", "Atomicity", "Durability", "C", "ADVANCED", "Atomicity means a transaction fully succeeds or fully rolls back."}
                },
                {
                    {"A program in execution is called a?", "File", "Process", "Compiler", "Folder", "B", "BEGINNER", "A process is a program in execution."},
                    {"Threads in one process commonly share?", "Address space", "Program counters", "Stacks", "CPU registers", "A", "INTERMEDIATE", "Threads share process resources such as the address space."},
                    {"Which technique moves fixed-size pages between disk and RAM?", "Paging", "Spooling", "Parsing", "Polling", "A", "ADVANCED", "Paging transfers pages between secondary storage and physical memory."}
                }
            };

            boolean seedQuestions = questions.count() == 0;
            for (int i = 0; i < savedSubjects.length; i++) {
                for (int j = 0; j < 3; j++) {
                    String topicName = topicNames[i][j];
                    Subject subject = savedSubjects[i];
                    String topicDescription = descriptions[i][j];
                    Topic topic = topics.findBySubjectId(subject.getId()).stream()
                        .filter(t -> t.getName().equals(topicName)).findFirst()
                        .orElseGet(() -> topics.save(new Topic(subject, topicName, topicDescription, "BEGINNER")));
                    if (seedQuestions) {
                        for (String[] raw : questionSets[i]) {
                            Question q = new Question();
                            q.setTopic(topic);
                            q.setQuestionText(raw[0]);
                            q.setOptionA(raw[1]); q.setOptionB(raw[2]);
                            q.setOptionC(raw[3]); q.setOptionD(raw[4]);
                            q.setCorrectAnswer(raw[5]); q.setDifficulty(raw[6]);
                            q.setExplanation(raw[7]);
                            questions.save(q);
                        }
                    }
                }
            }
            if (students.findByEmailIgnoreCase("demo@study.ai").isEmpty()) {
                Student demo = new Student();
                demo.setName("Demo Student");
                demo.setEmail("demo@study.ai");
                demo.setPasswordHash(new BCryptPasswordEncoder().encode("Study123!"));
                demo.setDepartment("Computer Science");
                demo.setSemester(2);
                demo.setLearningPreference("Visual");
                students.save(demo);
            }
        };
    }
}
