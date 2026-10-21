package edu.studyassistant.service;
import org.springframework.stereotype.Component;
/**
 * Transparent rule-based adaptive engine (no paid AI API).
 * Uses current accuracy, amount of evidence and recent trend to avoid overreacting
 * to one question. The returned level controls the next quiz's difficulty.
 */
@Component
public class AdaptiveLearningEngine {
 public String levelFor(double accuracy,int attempts,double recentAccuracy) {
   double blended = attempts < 4 ? (accuracy * 0.7 + 50.0 * 0.3) : accuracy;
   if (attempts >= 4) blended = blended * 0.8 + recentAccuracy * 0.2;
   if (blended < 40) return "BEGINNER";
   if (blended < 72) return "INTERMEDIATE";
   return "ADVANCED";
 }
 public String nextDifficulty(double accuracy,int attempts,double recentAccuracy) {
   return levelFor(accuracy,attempts,recentAccuracy);
 }
 public String priorityFor(double accuracy) { return accuracy < 40 ? "HIGH" : accuracy < 72 ? "MEDIUM" : "LOW"; }
 public String messageFor(String level,String topic) {
   return switch(level) {
     case "BEGINNER" -> "Revisit the fundamentals of " + topic + ", review the key ideas, then practise beginner questions.";
     case "INTERMEDIATE" -> "Strengthen your understanding of " + topic + " with worked examples and intermediate practice.";
     default -> "You are doing well in " + topic + ". Try advanced challenges and connect this topic to a related concept.";
   };
 }
}
