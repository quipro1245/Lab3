package com.example.lab2.repository;

import com.example.lab2.response.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {

    @Query("{name:'?0'}")
    Location findItemByName(String name);

    @Query(value = "{country:'?0'}", fields = "{'name' : 1, 'country' : 1}")
    List<Location> findAll(String country);

    long count();
}
