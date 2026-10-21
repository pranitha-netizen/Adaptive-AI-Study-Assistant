package edu.studyassistant.dto;
import jakarta.validation.constraints.*;
import java.util.List;
public class ApiDtos {
 public record RegisterRequest(@NotBlank String name,@Email @NotBlank String email,@Size(min=6) String password,String department,@Min(1) @Max(12) Integer semester,String learningPreference) {}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String password) {}
 public record StudentView(Long id,String name,String email,String department,Integer semester,String learningPreference) {}
 public record Answer(Long questionId,@NotBlank String answer) {}
 public record QuizSubmit(@NotNull Long studentId,@NotNull Long topicId,@NotEmpty List<Answer> answers) {}
 public record QuestionView(Long id,Long topicId,String topicName,String questionText,String optionA,String optionB,String optionC,String optionD,String difficulty) {}
 public record QuizResult(Long attemptId,int score,int totalQuestions,double percentage,String performanceLevel,String nextDifficulty,String message,List<AnswerReview> review) {}
 public record AnswerReview(Long questionId,String questionText,String selectedAnswer,String correctAnswer,boolean correct,String explanation) {}
}
