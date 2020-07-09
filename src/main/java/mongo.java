import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class mongo {

    public static void main(String[] args) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://ankitz:ankit@cluster0.9m7fa.mongodb.net/experiment?retryWrites=true&w=majority")) {
            MongoDatabase db = mongoClient.getDatabase("sample_training");
            MongoCollection<Document> zips = db.getCollection("zips");
            MongoCollection<Document> posts = db.getCollection("posts");
//            city ank = new city();
//            ank.threeMostPopulatedCitiesInTexas(zips);
//            post pos = new post();
//            pos.threeMostPopularTags(posts);
            segregation seg = new segregation();
            seg.getQuery(zips);
        }
    }

    public static Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }
}