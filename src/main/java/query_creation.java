import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class query_creation {
    public ArrayList<Bson> query = new ArrayList<Bson>();
    public void set_query(Bson stage){
        query.add(stage);
        System.out.println(query);

    }
    public void get_query(MongoCollection<Document> zips){
        List<Document> results = zips.aggregate(query)
                .into(new ArrayList<>());
        System.out.println("==> My Own Query");
        results.forEach(printDocuments());
    }

    private Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }

}

