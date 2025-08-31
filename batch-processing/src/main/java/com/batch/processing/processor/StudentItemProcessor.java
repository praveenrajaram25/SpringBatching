package com.batch.processing.processor;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.processing.model.Student;


@Configuration
public class StudentItemProcessor {
	
	public static final Logger logger =  LoggerFactory.getLogger(StudentItemProcessor.class);
	

	@Bean
	ItemProcessor<Student, Student> studentProcessor() {
		return student -> {
			
			String date = student.getBirthDate();
			String format ="";
			if(StringUtils.contains(date, "-")) {
				format="MM-dd-yyyy";		
			}else {
				format="MM/dd/yyyy";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			
			student.setDob(new java.sql.Date(sdf.parse(date).getTime()));
			student.setType(student.getType().substring(0, 1).toUpperCase()+(student.getType().substring(1).toLowerCase()));
			
			logger.info(student.toString());
			return student;
		};
	}
}
