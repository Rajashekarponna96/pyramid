package com.jpyramidv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pyramidacceptors.ptalk.api.event.PTalkEventListener;

@SpringBootApplication
public class JpyarmidV1Application {

	public static void main(String[] args) {
		SpringApplication.run(JpyarmidV1Application.class, args);
		System.out.println("Hello World123!");

	}

}