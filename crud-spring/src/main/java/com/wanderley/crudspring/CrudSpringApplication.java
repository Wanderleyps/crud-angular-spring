package com.wanderley.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.wanderley.crudspring.enums.Category;
import com.wanderley.crudspring.model.Course;
import com.wanderley.crudspring.model.Lesson;
import com.wanderley.crudspring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner initDatabase (CourseRepository courseRepository){
		return args -> {
			courseRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular + Spring");
			c.setCategory(Category.BACK_END);

			Lesson lesson = new Lesson();
			lesson.setName("Angular");
			lesson.setYoutubeUrl("watch?v=1");
			lesson.setCourse(c);
			c.getLessons().add(lesson);

			Lesson lesson2 = new Lesson();
			lesson2.setName("Java");
			lesson2.setYoutubeUrl("watch?v=2");
			lesson2.setCourse(c);
			c.getLessons().add(lesson2);

			courseRepository.save(c);

		};
	}

}
