package com.kchan.project.beyond.notes.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
 * Shot myself in the foot with the scope of the components when this class 
 * is in a sub-directory. Base package defined for that reason.
 * 
 * Should this class be moved to the base dir?
 */
@SpringBootApplication
@ComponentScan(basePackages="com.kchan.project.beyond.notes")
public class RunApplicationLocally {
	
	public static void main(String[] args) {
		SpringApplication.run(RunApplicationLocally.class, args);
	}
}
