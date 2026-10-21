package edu.studyassistant.service;
import edu.studyassistant.dto.ApiDtos.*;
import edu.studyassistant.model.*; import edu.studyassistant.repository.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class QuizService {
 private final QuestionRepository questions; private final TopicRepository topics; private final StudentRepository students;
 private final StudentTopicPerformanceRepository performances; private final QuizAttemptRepository attempts;
 private final RecommendationService recommendations; private final AdaptiveLearningEngine engine;
 public QuizService(QuestionRepository q,TopicRepository t,StudentRepository s,StudentTopicPerformanceRepository p,QuizAttemptRepository a,RecommendationService r,AdaptiveLearningEngine e){questions=q;topics=t;students=s;performances=p;attempts=a;recommendations=r;engine=e;}
 public List<QuestionView> questionsFor(Long topicId,Long studentId){
   Topic topic=topics.findById(topicId).orElseThrow(()->new IllegalArgumentException("Topic not found."));
   String level="BEGINNER";
   if(studentId!=null){Optional<StudentTopicPerformance> p=performances.findByStudentIdAndTopicId(studentId,topicId);if(p.isPresent())level=p.get().getCurrentLevel();}
   List<Question> selected=questions.findByTopicIdAndDifficultyIgnoreCase(topicId,level);
   if(selected.size()<5) selected=questions.findByTopicId(topicId);
   Collections.shuffle(selected);
   return selected.stream().limit(5).map(q->new QuestionView(q.getId(),topicId,topic.getName(),q.getQuestionText(),q.getOptionA(),q.getOptionB(),q.getOptionC(),q.getOptionD(),q.getDifficulty())).toList();
 }
 @Transactional public QuizResult submit(QuizSubmit req){
   Student student=students.findById(req.studentId()).orElseThrow(()->new IllegalArgumentException("Student not found."));
   Topic topic=topics.findById(req.topicId()).orElseThrow(()->new IllegalArgumentException("Topic not found."));
   if(req.answers().isEmpty())throw new IllegalArgumentException("Please answer at least one question.");
   int score=0;List<AnswerReview> review=new ArrayList<>();
   Set<Long> seen=new HashSet<>();
   for(Answer answer:req.answers()){
     if(!seen.add(answer.questionId()))throw new IllegalArgumentException("A question was submitted more than once.");
     Question q=questions.findById(answer.questionId()).orElseThrow(()->new IllegalArgumentException("Question not found: "+answer.questionId()));
     if(!q.getTopic().getId().equals(topic.getId()))throw new IllegalArgumentException("All questions must belong to the selected topic.");
     boolean correct=q.getCorrectAnswer().equalsIgnoreCase(answer.answer().trim());
     if(correct)score++;
     review.add(new AnswerReview(q.getId(),q.getQuestionText(),answer.answer().toUpperCase(),q.getCorrectAnswer(),correct,q.getExplanation()));
   }
   int total=req.answers().size();double pct=Math.round(score*1000.0/total)/10.0;
   StudentTopicPerformance p=performances.findByStudentIdAndTopicId(student.getId(),topic.getId()).orElseGet(()->{StudentTopicPerformance x=new StudentTopicPerformance();x.setStudent(student);x.setTopic(topic);return x;});
   p.setQuestionsAttempted(p.getQuestionsAttempted()+total);p.setCorrectAnswers(p.getCorrectAnswers()+score);
   p.setAccuracy(Math.round(p.getCorrectAnswers()*1000.0/p.getQuestionsAttempted())/10.0);
   p.setCurrentLevel(engine.levelFor(p.getAccuracy(),p.getQuestionsAttempted(),pct));performances.save(p);
   QuizAttempt a=new QuizAttempt();a.setStudent(student);a.setSubject(topic.getSubject());a.setTopic(topic);a.setScore(score);a.setTotalQuestions(total);a.setPercentage(pct);a.setDifficulty(p.getCurrentLevel());attempts.save(a);
   recommendations.refresh(student.getId());
   String next=engine.nextDifficulty(p.getAccuracy(),p.getQuestionsAttempted(),pct);
   return new QuizResult(a.getId(),score,total,pct,p.getCurrentLevel(),next,score==total?"Perfect work! Keep challenging yourself.": "Review the explanations below, then follow your personalised recommendation.",review);
 }
}
