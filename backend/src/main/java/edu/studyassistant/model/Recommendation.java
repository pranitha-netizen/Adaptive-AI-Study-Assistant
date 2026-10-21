package edu.studyassistant.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="recommendations")
public class Recommendation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student;
 @ManyToOne(optional=false) @JoinColumn(name="topic_id") private Topic topic;
 private String recommendationType; @Column(length=1200) private String message; private String priority;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Recommendation() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Student getStudent(){return student;} public void setStudent(Student v){student=v;}
 public Topic getTopic(){return topic;} public void setTopic(Topic v){topic=v;}
 public String getRecommendationType(){return recommendationType;} public void setRecommendationType(String v){recommendationType=v;}
 public String getMessage(){return message;} public void setMessage(String v){message=v;}
 public String getPriority(){return priority;} public void setPriority(String v){priority=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
