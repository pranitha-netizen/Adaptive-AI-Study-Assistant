package edu.studyassistant.model;
import jakarta.persistence.*;
@Entity @Table(name="topics")
public class Topic {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="subject_id") private Subject subject;
 @Column(nullable=false) private String name;
 @Column(length=1000) private String description;
 private String difficulty="BEGINNER";
 public Topic() {} public Topic(Subject s,String n,String d,String level){subject=s;name=n;description=d;difficulty=level;}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Subject getSubject(){return subject;} public void setSubject(Subject v){subject=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getDifficulty(){return difficulty;} public void setDifficulty(String v){difficulty=v;}
}
