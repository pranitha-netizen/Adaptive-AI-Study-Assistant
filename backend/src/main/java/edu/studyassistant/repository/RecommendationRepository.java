package edu.studyassistant.repository;
import edu.studyassistant.model.Recommendation; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface RecommendationRepository extends JpaRepository<Recommendation,Long>{ java.util.List<Recommendation> findByStudentIdOrderByCreatedAtDesc(Long studentId); }
