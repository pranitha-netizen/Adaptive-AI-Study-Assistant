package edu.studyassistant.repository;
import edu.studyassistant.model.Question; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface QuestionRepository extends JpaRepository<Question,Long>{ java.util.List<Question> findByTopicIdAndDifficultyIgnoreCase(Long topicId,String difficulty); java.util.List<Question> findByTopicId(Long topicId); }
