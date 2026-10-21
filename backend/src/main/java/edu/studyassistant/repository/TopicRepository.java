package edu.studyassistant.repository;
import edu.studyassistant.model.Topic; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface TopicRepository extends JpaRepository<Topic,Long>{ java.util.List<Topic> findBySubjectId(Long subjectId); }
