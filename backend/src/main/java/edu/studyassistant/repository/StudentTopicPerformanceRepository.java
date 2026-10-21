package edu.studyassistant.repository;
import edu.studyassistant.model.StudentTopicPerformance; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface StudentTopicPerformanceRepository extends JpaRepository<StudentTopicPerformance,Long>{ java.util.List<StudentTopicPerformance> findByStudentId(Long studentId); java.util.Optional<StudentTopicPerformance> findByStudentIdAndTopicId(Long studentId,Long topicId); }
