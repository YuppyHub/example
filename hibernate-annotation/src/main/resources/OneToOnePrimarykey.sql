create table STUDENT (
   student_id BIGINT NOT NULL AUTO_INCREMENT,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   section    VARCHAR(30) NOT NULL,
   PRIMARY KEY (student_id)
);
 
 
create table ADDRESS (
   address_id BIGINT NOT NULL,
   street VARCHAR(30) NOT NULL,
   city  VARCHAR(30) NOT NULL,
   country  VARCHAR(30) NOT NULL,
   PRIMARY KEY (address_id),
   CONSTRAINT student_address FOREIGN KEY (address_id) REFERENCES STUDENT ( student_id) ON DELETE CASCADE 
);