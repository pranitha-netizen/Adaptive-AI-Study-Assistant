package edu.studyassistant.service;
import edu.studyassistant.model.*; import edu.studyassistant.repository.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class RecommendationService {
 private final RecommendationRepository recs; private final StudentTopicPerformanceRepository perf; private final TopicRepository topics; private final StudentRepository students; private final AdaptiveLearningEngine engine;
 public RecommendationService(RecommendationRepository r,StudentTopicPerformanceRepository p,TopicRepository t,StudentRepository s,AdaptiveLearningEngine e){recs=r;perf=p;topics=t;students=s;engine=e;}
 @Transactional public void refresh(Long studentId){
   Student student=students.findById(studentId).orElseThrow(()->new IllegalArgumentException("Student not found."));
   List<StudentTopicPerformance> rows=perf.findByStudentId(studentId);
   for(StudentTopicPerformance p:rows){
     double recent=p.getAccuracy();
     String level=engine.levelFor(p.getAccuracy(),p.getQuestionsAttempted(),recent);
     p.setCurrentLevel(level);
     Recommendation r=new Recommendation();r.setStudent(student);r.setTopic(p.getTopic());r.setRecommendationType(level);
     r.setPriority(engine.priorityFor(p.getAccuracy()));r.setMessage(engine.messageFor(level,p.getTopic().getName()));recs.save(r);
   }
 }
 public List<Map<String,Object>> list(Long studentId){
   List<Map<String,Object>> out=new ArrayList<>();
   for(Recommendation r:recs.findByStudentIdOrderByCreatedAtDesc(studentId)){
     Map<String,Object> m=new LinkedHashMap<>();m.put("id",r.getId());m.put("topicId",r.getTopic().getId());m.put("topicName",r.getTopic().getName());m.put("subjectName",r.getTopic().getSubject().getName());m.put("recommendationType",r.getRecommendationType());m.put("message",r.getMessage());m.put("priority",r.getPriority());m.put("createdAt",r.getCreatedAt());out.add(m);
   } return out;
 }
}
