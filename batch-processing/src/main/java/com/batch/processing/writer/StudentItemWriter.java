package com.batch.processing.writer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.batch.processing.model.Student;
import org.springframework.batch.item.database.JpaItemWriter;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class StudentItemWriter {
	
	@Bean
	JpaItemWriter<Student> studentWriter(EntityManagerFactory emf) {
	    JpaItemWriter<Student> writer = new JpaItemWriter<>();
	    writer.setEntityManagerFactory(emf);
	    return writer;
	}

}
