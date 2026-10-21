package edu.studyassistant.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="students")
public class Student {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String passwordHash;
 private String department; private Integer semester;
 private String learningPreference="Visual";
 private LocalDateTime createdAt=LocalDateTime.now();
 public Student() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public Integer getSemester(){return semester;} public void setSemester(Integer v){semester=v;}
 public String getLearningPreference(){return learningPreference;} public void setLearningPreference(String v){learningPreference=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
