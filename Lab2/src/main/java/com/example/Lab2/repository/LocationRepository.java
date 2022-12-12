package com.example.Lab2.repository;

import com.example.Lab2.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LocationRepository extends MongoRepository<Location,String> {

    @Query("{name:'?0'}")
    Location findItemByName(String name);

    @Query(value="{country:'?0'}", fields="{'name' : 1, 'country' : 1}")
    List<Location> findAll(String country);

    public long count();
}
