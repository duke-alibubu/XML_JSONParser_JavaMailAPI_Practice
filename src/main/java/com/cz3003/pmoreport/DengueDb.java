package com.cz3003.pmoreport;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.bson.Document;

public class DengueDb {

    private MongoCollection<Document> collection;
    public DengueDb() {

        String host = System.getenv("DB_HOST");
        if (host == null) {
            host = "localhost";
        }
        MongoClient client = new MongoClient(host);
        MongoDatabase db = client.getDatabase("cms");
        collection = db.getCollection("dengues");
    }

    public List<Document> getDengueCases() {

        List<Document> reports = new ArrayList<>();

        for (Document doc : collection.find()) {

            reports.add(doc);
        }
        return reports;

    }


}

