package edu.studyassistant.controller;
import edu.studyassistant.dto.ApiDtos.*; import edu.studyassistant.model.*; import edu.studyassistant.repository.*;
import edu.studyassistant.service.*; import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin(origins = "*")
public class ApiController {
 private final StudentService students; private final StudentRepository studentRepo; private final SubjectRepository subjects; private final TopicRepository topics; private final QuestionRepository questions; private final QuizService quizzes; private final QuizAttemptRepository attempts; private final StudentTopicPerformanceRepository performances; private final RecommendationService recommendations;
 public ApiController(StudentService s,StudentRepository sr,SubjectRepository sub,TopicRepository t,QuestionRepository q,QuizService quiz,QuizAttemptRepository a,StudentTopicPerformanceRepository p,RecommendationService r){students=s;studentRepo=sr;subjects=sub;topics=t;questions=q;quizzes=quiz;attempts=a;performances=p;recommendations=r;}
 @PostMapping("/students/register") public StudentView register(@Valid @RequestBody RegisterRequest r){return students.register(r);}
 @PostMapping("/students/login") public StudentView login(@Valid @RequestBody LoginRequest r){return students.login(r);}
 @GetMapping("/subjects") public List<Subject> allSubjects(){return subjects.findAll();}
 @GetMapping("/subjects/{id}/topics") public List<Topic> subjectTopics(@PathVariable Long id){if(!subjects.existsById(id))throw new IllegalArgumentException("Subject not found.");return topics.findBySubjectId(id);}
 @GetMapping("/topics/{id}/questions") public List<QuestionView> topicQuestions(@PathVariable Long id,@RequestParam(required=false) Long studentId){return quizzes.questionsFor(id,studentId);}
 @PostMapping("/quizzes/submit") public QuizResult submit(@Valid @RequestBody QuizSubmit r){return quizzes.submit(r);}
 @GetMapping("/students/{id}/performance") public List<Map<String,Object>> performance(@PathVariable Long id){students.get(id);List<Map<String,Object>> out=new ArrayList<>();for(StudentTopicPerformance p:performances.findByStudentId(id)){Map<String,Object> m=new LinkedHashMap<>();m.put("topicId",p.getTopic().getId());m.put("topicName",p.getTopic().getName());m.put("subjectName",p.getTopic().getSubject().getName());m.put("questionsAttempted",p.getQuestionsAttempted());m.put("correctAnswers",p.getCorrectAnswers());m.put("accuracy",p.getAccuracy());m.put("currentLevel",p.getCurrentLevel());out.add(m);}return out;}
 @GetMapping("/students/{id}/recommendations") public List<Map<String,Object>> recs(@PathVariable Long id){students.get(id);return recommendations.list(id);}
 @GetMapping("/students/{id}/dashboard") public Map<String,Object> dashboard(@PathVariable Long id){
   Student student=students.get(id);List<QuizAttempt> history=attempts.findByStudentIdOrderByCompletedAtDesc(id);List<StudentTopicPerformance> perf=performances.findByStudentId(id);
   double avg=history.isEmpty()?0:Math.round(history.stream().mapToDouble(QuizAttempt::getPercentage).average().orElse(0)*10)/10.0;
   StudentTopicPerformance strongest=perf.stream().max(Comparator.comparingDouble(StudentTopicPerformance::getAccuracy)).orElse(null);
   StudentTopicPerformance weakest=perf.stream().min(Comparator.comparingDouble(StudentTopicPerformance::getAccuracy)).orElse(null);
   Map<String,Object> m=new LinkedHashMap<>();m.put("student",students.view(student));m.put("quizzesCompleted",history.size());m.put("averageScore",avg);m.put("topicsPractised",perf.size());m.put("strongestTopic",strongest==null?"Not yet available":strongest.getTopic().getName());m.put("weakestTopic",weakest==null?"Not yet available":weakest.getTopic().getName());m.put("learningLevel",perf.isEmpty()?"BEGINNER":perf.stream().map(StudentTopicPerformance::getCurrentLevel).max(Comparator.comparingInt(x->switch(x){case "ADVANCED"->3;case "INTERMEDIATE"->2;default->1;})).orElse("BEGINNER"));m.put("recentAttempts",history.stream().limit(5).map(a->Map.of("id",a.getId(),"subject",a.getSubject().getName(),"topic",a.getTopic()==null?"General":a.getTopic().getName(),"score",a.getScore(),"totalQuestions",a.getTotalQuestions(),"percentage",a.getPercentage(),"completedAt",a.getCompletedAt())).toList());m.put("recommendations",recommendations.list(id).stream().limit(3).toList());m.put("performance",performance(id));return m;
 }
 @PutMapping("/students/{id}") public StudentView update(@PathVariable Long id,@RequestBody Map<String,Object> body){return students.update(id,body);}
 @ExceptionHandler({IllegalArgumentException.class,org.springframework.dao.DataIntegrityViolationException.class})
 @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
 public Map<String,String> handleBadRequest(Exception e){return Map.of("error",e instanceof org.springframework.dao.DataIntegrityViolationException?"That email is already registered.":(e.getMessage()==null?"Invalid request.":e.getMessage()));}
}
