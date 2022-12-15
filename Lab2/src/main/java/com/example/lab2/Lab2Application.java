package com.example.lab2;

import com.example.lab2.controller.RestSpringBootController;
import com.example.lab2.model.Location;
import com.example.lab2.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Lab2Application implements CommandLineRunner {

    @Autowired
    LocationRepository locationItemRepo;
    RestSpringBootController restSpringBootController;

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    //CREATE
    void createGroceryItems() {

        System.out.println("Data creation started...");
        locationItemRepo.save(new Location("2801268", "London1", "City of London, Greater London", "United Kingdom", 51.52, -0.11, "london-city-of-london-greater-london-united-kingdom"));
        locationItemRepo.save(new Location("2782994", "Brixton", "Lambeth, Greater London", "United Kingdom", 51.46, -0.11, "brixton-lambeth-greater-london-united-kingdom"));
        System.out.println("Data creation complete...");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-------------CREATE GROCERY ITEMS-------------------------------\n");

        //createGroceryItems();
    }
}
