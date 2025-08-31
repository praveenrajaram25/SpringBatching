package com.batch.processing.reader;

import com.batch.processing.model.Student;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class StudentItemReader {

	@Bean
	FlatFileItemReader<Student> studentReader() {
		return new FlatFileItemReaderBuilder<Student>()
				.name("studentReader")
				.resource(new FileSystemResource("src/main/resources/students.csv"))
				.delimited()
				.names("name", "department", "age", "birthDate", "address", "type", "year", "batch", "cgpa")
				.linesToSkip(0)
				.targetType(Student.class)
				.build();
	}

}
