import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;


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
            query_creation cont = seg.getQuery();


            cont.get_query(zips);
        }
    }

    public static Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }
}