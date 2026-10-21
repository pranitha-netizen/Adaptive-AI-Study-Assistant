package edu.studyassistant.repository;
import edu.studyassistant.model.Subject; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface SubjectRepository extends JpaRepository<Subject,Long>{  }
