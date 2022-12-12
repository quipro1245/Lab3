package com.example.Lab2.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionMongoDB {


    final static Logger logger = LogManager.getLogger(RestSpringBootController.class);

    public MongoDatabase getMongoDatabase(MongoClient mongoClient, String databaseName) {

        return mongoClient.getDatabase(databaseName);

    }

    public MongoClient getMongoClient(String url) {
        return MongoClients.create(url);
    }
}
