package edu.studyassistant.service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class AdaptiveLearningEngineTest {
 private final AdaptiveLearningEngine engine=new AdaptiveLearningEngine();
 @Test void lowAccuracySuggestsBeginner(){assertEquals("BEGINNER",engine.levelFor(20,10,25));}
 @Test void mediumAccuracySuggestsIntermediate(){assertEquals("INTERMEDIATE",engine.levelFor(55,10,60));}
 @Test void highAccuracySuggestsAdvanced(){assertEquals("ADVANCED",engine.levelFor(90,10,85));}
 @Test void priorityMatchesAccuracy(){assertEquals("HIGH",engine.priorityFor(25));assertEquals("MEDIUM",engine.priorityFor(55));assertEquals("LOW",engine.priorityFor(90));}
}
