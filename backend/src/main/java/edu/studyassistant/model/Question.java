package edu.studyassistant.model;
import jakarta.persistence.*;
@Entity @Table(name="questions")
public class Question {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="topic_id") private Topic topic;
 @Column(nullable=false,length=1200) private String questionText;
 @Column(nullable=false) private String optionA; @Column(nullable=false) private String optionB;
 @Column(nullable=false) private String optionC; @Column(nullable=false) private String optionD;
 @Column(nullable=false) private String correctAnswer;
 @Column(nullable=false) private String difficulty="BEGINNER";
 @Column(length=1200) private String explanation;
 public Question() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Topic getTopic(){return topic;} public void setTopic(Topic v){topic=v;}
 public String getQuestionText(){return questionText;} public void setQuestionText(String v){questionText=v;}
 public String getOptionA(){return optionA;} public void setOptionA(String v){optionA=v;}
 public String getOptionB(){return optionB;} public void setOptionB(String v){optionB=v;}
 public String getOptionC(){return optionC;} public void setOptionC(String v){optionC=v;}
 public String getOptionD(){return optionD;} public void setOptionD(String v){optionD=v;}
 public String getCorrectAnswer(){return correctAnswer;} public void setCorrectAnswer(String v){correctAnswer=v;}
 public String getDifficulty(){return difficulty;} public void setDifficulty(String v){difficulty=v;}
 public String getExplanation(){return explanation;} public void setExplanation(String v){explanation=v;}
}
