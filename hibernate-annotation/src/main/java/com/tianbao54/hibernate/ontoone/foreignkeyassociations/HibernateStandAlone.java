package com.tianbao54.hibernate.ontoone.foreignkeyassociations;

import java.util.List;

import org.hibernate.Session;

import com.tianbao54.hibernate.ontoone.foreignkeyassociations.model.Address;
import com.tianbao54.hibernate.ontoone.foreignkeyassociations.model.Student;

public class HibernateStandAlone {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Student student = new Student("Sam","Disilva","Maths");
		Address address = new Address("10 Silver street","NYC","USA");
		
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		session.persist(address);
		student.setAddress(address);
		session.persist(student);
		
		List<Student> students = (List<Student>)session.createQuery("from Student ").list();
		for(Student s: students){
			System.out.println("Details : "+s);
		}
		
		session.getTransaction().commit();
		session.close();  
	}

}
