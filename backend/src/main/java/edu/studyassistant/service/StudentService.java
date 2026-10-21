package edu.studyassistant.service;
import edu.studyassistant.dto.ApiDtos.*;
import edu.studyassistant.model.Student;
import edu.studyassistant.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Map;
import java.util.HashMap;
@Service
public class StudentService {
 private final StudentRepository repo; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public StudentService(StudentRepository repo){this.repo=repo;}
 @Transactional public StudentView register(RegisterRequest r) {
   if(repo.findByEmailIgnoreCase(r.email()).isPresent()) throw new IllegalArgumentException("An account with this email already exists.");
   Student s=new Student(); s.setName(r.name().trim()); s.setEmail(r.email().trim().toLowerCase());
   s.setPasswordHash(encoder.encode(r.password())); s.setDepartment(r.department()); s.setSemester(r.semester());
   s.setLearningPreference(r.learningPreference()==null||r.learningPreference().isBlank()?"Visual":r.learningPreference());
   return view(repo.save(s));
 }
 public StudentView login(LoginRequest r) {
   Student s=repo.findByEmailIgnoreCase(r.email()).orElseThrow(()->new IllegalArgumentException("Email or password is incorrect."));
   if(!encoder.matches(r.password(),s.getPasswordHash())) throw new IllegalArgumentException("Email or password is incorrect.");
   return view(s);
 }
 public Student get(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Student not found."));}
 @Transactional public StudentView update(Long id,Map<String,Object> data) {
   Student s=get(id);
   if(data.containsKey("name")) s.setName(String.valueOf(data.get("name")).trim());
   if(data.containsKey("department")) s.setDepartment(String.valueOf(data.get("department")));
   if(data.containsKey("semester")) s.setSemester(Integer.valueOf(String.valueOf(data.get("semester"))));
   if(data.containsKey("learningPreference")) s.setLearningPreference(String.valueOf(data.get("learningPreference")));
   return view(repo.save(s));
 }
 public StudentView view(Student s){return new StudentView(s.getId(),s.getName(),s.getEmail(),s.getDepartment(),s.getSemester(),s.getLearningPreference());}
}
