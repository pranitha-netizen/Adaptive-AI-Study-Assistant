CREATE DATABASE IF NOT EXISTS adaptive_study_assistant CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE adaptive_study_assistant;

CREATE TABLE IF NOT EXISTS students (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL,
 department VARCHAR(255), semester INT, learning_preference VARCHAR(255) DEFAULT 'Visual',
 created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)
);
CREATE TABLE IF NOT EXISTS subjects (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL UNIQUE, description VARCHAR(1000)
);
CREATE TABLE IF NOT EXISTS topics (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, subject_id BIGINT NOT NULL, name VARCHAR(255) NOT NULL,
 description VARCHAR(1000), difficulty VARCHAR(255) DEFAULT 'BEGINNER',
 CONSTRAINT fk_topics_subject FOREIGN KEY(subject_id) REFERENCES subjects(id)
);
CREATE TABLE IF NOT EXISTS questions (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, topic_id BIGINT NOT NULL, question_text VARCHAR(1200) NOT NULL,
 option_a VARCHAR(255) NOT NULL, option_b VARCHAR(255) NOT NULL, option_c VARCHAR(255) NOT NULL, option_d VARCHAR(255) NOT NULL,
 correct_answer VARCHAR(255) NOT NULL, difficulty VARCHAR(255) NOT NULL, explanation VARCHAR(1200),
 CONSTRAINT fk_questions_topic FOREIGN KEY(topic_id) REFERENCES topics(id)
);
CREATE TABLE IF NOT EXISTS quiz_attempts (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, student_id BIGINT NOT NULL, subject_id BIGINT NOT NULL, topic_id BIGINT,
 score INT NOT NULL, total_questions INT NOT NULL, percentage DOUBLE NOT NULL, difficulty VARCHAR(255),
 completed_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
 CONSTRAINT fk_attempt_student FOREIGN KEY(student_id) REFERENCES students(id),
 CONSTRAINT fk_attempt_subject FOREIGN KEY(subject_id) REFERENCES subjects(id),
 CONSTRAINT fk_attempt_topic FOREIGN KEY(topic_id) REFERENCES topics(id)
);
CREATE TABLE IF NOT EXISTS student_topic_performance (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, student_id BIGINT NOT NULL, topic_id BIGINT NOT NULL,
 questions_attempted INT NOT NULL DEFAULT 0, correct_answers INT NOT NULL DEFAULT 0,
 accuracy DOUBLE NOT NULL DEFAULT 0, current_level VARCHAR(255) DEFAULT 'BEGINNER',
 CONSTRAINT uq_student_topic UNIQUE(student_id,topic_id),
 CONSTRAINT fk_performance_student FOREIGN KEY(student_id) REFERENCES students(id),
 CONSTRAINT fk_performance_topic FOREIGN KEY(topic_id) REFERENCES topics(id)
);
CREATE TABLE IF NOT EXISTS recommendations (
 id BIGINT PRIMARY KEY AUTO_INCREMENT, student_id BIGINT NOT NULL, topic_id BIGINT NOT NULL,
 recommendation_type VARCHAR(255), message VARCHAR(1200), priority VARCHAR(255),
 created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
 CONSTRAINT fk_recommendation_student FOREIGN KEY(student_id) REFERENCES students(id),
 CONSTRAINT fk_recommendation_topic FOREIGN KEY(topic_id) REFERENCES topics(id)
);
