package edu.studyassistant.model;
import jakarta.persistence.*;
@Entity @Table(name="student_topic_performance", uniqueConstraints=@UniqueConstraint(columnNames={"student_id","topic_id"}))
public class StudentTopicPerformance {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student;
 @ManyToOne(optional=false) @JoinColumn(name="topic_id") private Topic topic;
 private int questionsAttempted; private int correctAnswers; private double accuracy; private String currentLevel="BEGINNER";
 public StudentTopicPerformance() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Student getStudent(){return student;} public void setStudent(Student v){student=v;}
 public Topic getTopic(){return topic;} public void setTopic(Topic v){topic=v;}
 public int getQuestionsAttempted(){return questionsAttempted;} public void setQuestionsAttempted(int v){questionsAttempted=v;}
 public int getCorrectAnswers(){return correctAnswers;} public void setCorrectAnswers(int v){correctAnswers=v;}
 public double getAccuracy(){return accuracy;} public void setAccuracy(double v){accuracy=v;}
 public String getCurrentLevel(){return currentLevel;} public void setCurrentLevel(String v){currentLevel=v;}
}
