package com.github.ifsantos;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.ifsantos.courses.api.model.Course;
import com.github.ifsantos.courses.repository.CourseRepository;

@SpringBootApplication
public class PdfWatermarkWriterApplication {
	static Logger log = LoggerFactory.getLogger("PdfWatermarkWriterApplication");

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
	public CommandLineRunner printArray(){
		
		return args -> {
			log.info("Testing print array");
			int size=10;
			
			Integer[] arr = new Integer[size];
			for (int i=0; i < size; i++) {
				arr[i] = i;
			}
			String collect = Stream.of(arr).sorted()
			.map(v -> Integer.toString(v))
			.collect(Collectors.joining("|-|"));
			log.info( collect);
		};
	}
}
