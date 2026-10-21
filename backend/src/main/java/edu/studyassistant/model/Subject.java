package edu.studyassistant.model;
import jakarta.persistence.*;
@Entity @Table(name="subjects")
public class Subject {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String name;
 @Column(length=1000) private String description;
 public Subject() {} public Subject(String n,String d){name=n;description=d;}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
