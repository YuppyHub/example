create table UNIVERSITY (
   university_id BIGINT NOT NULL AUTO_INCREMENT,
   name VARCHAR(30) NOT NULL,
   country  VARCHAR(30) NOT NULL,
   PRIMARY KEY (university_id)
);
 
create table STUDENT (
   student_id BIGINT NOT NULL AUTO_INCREMENT,
   university_id BIGINT NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   section    VARCHAR(30) NOT NULL,
   PRIMARY KEY (student_id),
   CONSTRAINT student_university FOREIGN KEY (university_id) REFERENCES UNIVERSITY (university_id) ON UPDATE CASCADE ON DELETE CASCADE
);