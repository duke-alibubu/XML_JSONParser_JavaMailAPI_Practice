package com.cz3003.pmoreport;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.bson.Document;

public class ReportDb {

	private static final long TIME_30_MIN = 1000 * 60 * 30;
    private MongoCollection<Document> collection;
    public ReportDb() {

        String host = System.getenv("DB_HOST");
        if (host == null) {
            host = "localhost";
        }
        MongoClient client = new MongoClient(host);
		MongoDatabase db = client.getDatabase("test_database");
        collection = db.getCollection("reports");
    }

	public List<Document> getReportsPast30Min() {

		List<Document> reports = new ArrayList<>();

		for (Document doc : collection.find(gt("time_last_updated", new Date(System.currentTimeMillis() - TIME_30_MIN)))) {

			reports.add(doc);
		}
		return reports;

	}


}
