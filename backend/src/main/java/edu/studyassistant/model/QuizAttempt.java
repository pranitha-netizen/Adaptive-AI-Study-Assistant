package edu.studyassistant.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="quiz_attempts")
public class QuizAttempt {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student;
 @ManyToOne(optional=false) @JoinColumn(name="subject_id") private Subject subject;
 @ManyToOne @JoinColumn(name="topic_id") private Topic topic;
 private int score; private int totalQuestions; private double percentage;
 private String difficulty; private LocalDateTime completedAt=LocalDateTime.now();
 public QuizAttempt() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Student getStudent(){return student;} public void setStudent(Student v){student=v;}
 public Subject getSubject(){return subject;} public void setSubject(Subject v){subject=v;}
 public Topic getTopic(){return topic;} public void setTopic(Topic v){topic=v;}
 public int getScore(){return score;} public void setScore(int v){score=v;}
 public int getTotalQuestions(){return totalQuestions;} public void setTotalQuestions(int v){totalQuestions=v;}
 public double getPercentage(){return percentage;} public void setPercentage(double v){percentage=v;}
 public String getDifficulty(){return difficulty;} public void setDifficulty(String v){difficulty=v;}
 public LocalDateTime getCompletedAt(){return completedAt;} public void setCompletedAt(LocalDateTime v){completedAt=v;}
}
