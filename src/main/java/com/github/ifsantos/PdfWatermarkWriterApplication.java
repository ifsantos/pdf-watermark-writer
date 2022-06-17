package com.github.ifsantos;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.ifsantos.courses.api.model.Course;
import com.github.ifsantos.courses.repository.CourseRepository;
import com.github.ifsantos.xml.NfeProcessor;

@SpringBootApplication
public class PdfWatermarkWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfWatermarkWriterApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDB(CourseRepository repo){
		
		return args -> {
			repo.deleteAll();
			
			Course course = new Course();
			course.setName("Java+Spring");
			course.setCategory("back-end");
			
			repo.save(course);
		};
	}
	@Bean
	public CommandLineRunner loadNfe(CourseRepository repo){
		
		return args -> {
			(new NfeProcessor()).run();
		};
	}
}
