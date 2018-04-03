package com.tianbao54.hibernate.manytoone.unidirectional;

import java.util.List;

import org.hibernate.Session;

import com.tianbao54.hibernate.manytoone.unidirectional.model.Student;
import com.tianbao54.hibernate.manytoone.unidirectional.model.University;


public class HibernateStandAlone {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Student student1 = new Student("Sam","Disilva","Maths");
		Student student2 = new Student("Joshua", "Brill", "Science");
		Student student3 = new Student("Peter", "Pan", "Physics");
		
		University university = new University("CAMBRIDGE", "ENGLAND");

		student1.setUniversity(university);
		student2.setUniversity(university);
		student3.setUniversity(university);


		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.persist(university);
		
		session.persist(student1);
		session.persist(student2);
		session.persist(student3);
		
		List<Student> students = (List<Student>)session.createQuery("from Student ").list();
		for(Student s: students){
			System.out.println("Student Details : "+s);
			System.out.println("Student University Details: "+s.getUniversity());
		}
		
		session.getTransaction().commit();
		session.close();  
	}

}
