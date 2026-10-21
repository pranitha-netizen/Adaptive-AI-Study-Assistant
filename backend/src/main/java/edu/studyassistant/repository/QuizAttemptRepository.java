package edu.studyassistant.repository;
import edu.studyassistant.model.QuizAttempt; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt,Long>{ java.util.List<QuizAttempt> findByStudentIdOrderByCompletedAtDesc(Long studentId); }
