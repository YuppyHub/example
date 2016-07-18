create table ADDRESS (
   address_id BIGINT NOT NULL AUTO_INCREMENT,
   street VARCHAR(30) NOT NULL,
   city  VARCHAR(30) NOT NULL,
   country  VARCHAR(30) NOT NULL,
   PRIMARY KEY (address_id)
);
 
create table STUDENT (
   student_id BIGINT NOT NULL AUTO_INCREMENT,
   home_address_id BIGINT NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   section    VARCHAR(30) NOT NULL,
   PRIMARY KEY (student_id),
   CONSTRAINT student_address FOREIGN KEY (home_address_id) REFERENCES ADDRESS ( address_id)
);