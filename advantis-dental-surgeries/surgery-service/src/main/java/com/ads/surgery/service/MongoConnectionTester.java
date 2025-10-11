package com.ads.surgery.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoConnectionTester implements CommandLineRunner {

    @Autowired
    private MongoClient mongoClient;

    @Override
    public void run(String... args) {
        System.out.println("🔍 Testing MongoDB connection...");
        try {
            // Try connecting to your configured database
            MongoDatabase db = mongoClient.getDatabase("surgerydb");
            db.runCommand(new org.bson.Document("ping", 1));
            System.out.println("✅ MongoDB connection successful!");
        } catch (Exception e) {
            System.out.println("❌ MongoDB connection failed:");
            e.printStackTrace(System.out);
        }
    }
}
