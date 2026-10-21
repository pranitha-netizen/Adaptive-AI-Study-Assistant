USE adaptive_study_assistant;
INSERT IGNORE INTO subjects(name,description) VALUES
('Java Programming','Core Java and object-oriented programming'),
('Data Structures','Essential data structures and algorithms'),
('DBMS','Relational databases and SQL'),
('Operating Systems','Processes, memory and operating-system concepts');

INSERT INTO topics(subject_id,name,description,difficulty)
SELECT s.id,x.name,x.description,'BEGINNER' FROM subjects s JOIN (
 SELECT 'Java Programming' subject_name,'OOP' name,'Classes, objects, encapsulation and polymorphism' description UNION ALL
 SELECT 'Java Programming','Inheritance','Extending classes and reusing behaviour' UNION ALL
 SELECT 'Java Programming','Exception Handling','Handling runtime errors safely' UNION ALL
 SELECT 'Data Structures','Arrays','Array operations and complexity' UNION ALL
 SELECT 'Data Structures','Stacks and Queues','LIFO and FIFO data structures' UNION ALL
 SELECT 'Data Structures','Searching','Finding items efficiently' UNION ALL
 SELECT 'DBMS','SQL Basics','Queries, filtering and joins' UNION ALL
 SELECT 'DBMS','Normalization','Reducing redundancy with normal forms' UNION ALL
 SELECT 'DBMS','Transactions','ACID properties and concurrent updates' UNION ALL
 SELECT 'Operating Systems','Processes and Threads','Process states and thread basics' UNION ALL
 SELECT 'Operating Systems','Memory Management','Paging and virtual memory' UNION ALL
 SELECT 'Operating Systems','Scheduling','CPU scheduling approaches'
) x ON x.subject_name=s.name
WHERE NOT EXISTS (SELECT 1 FROM topics t WHERE t.subject_id=s.id AND t.name=x.name);

-- Demo questions are also seeded automatically by the Spring Boot application on its first run.
-- This script intentionally avoids inserting duplicate question rows when the app is also used.
