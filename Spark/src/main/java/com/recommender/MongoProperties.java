package com.recommender;

import com.mongodb.MongoClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Properties;

public class MongoProperties implements Serializable {
    final Properties prop = new Properties();
    private MongoClient mongoClient;

    public MongoProperties(){
        final InputStream resourceAsStream = this.getClass().getResourceAsStream("/mongo.conf");
        try {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost(){
        return prop.getProperty("host");
    }

    public String getDB(){
        return prop.getProperty("db");
    }

    public String getInputCollection(){
        return prop.getProperty("collection");
    }

    public String getOutputCollection(){
        return prop.getProperty("outputCollection");
    }
    public int getPort() {
        return Integer.parseInt(prop.getProperty("port"));
    }

    public MongoClient getMongoClient() throws UnknownHostException {
        return new MongoClient(getHost(),getPort());
    }
    public String username(){
        return prop.getProperty("username");
    }
    public String password(){
        return prop.getProperty("password");
    }
}
