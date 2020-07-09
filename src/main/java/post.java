import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

public class post {
    public static void threeMostPopularTags(MongoCollection<Document> posts) {
        Bson unwind = unwind("$tags");
        Bson group = group("$tags", sum("count", 1L), push("titles", "$title"));
        Bson sort = sort(descending("count"));
        Bson limit = limit(3);
        Bson project = project(fields(excludeId(), computed("tag", "$_id"), include("count", "titles")));

        List<Document> results = posts.aggregate(Arrays.asList(unwind, group, sort, limit, project)).into(new ArrayList<>());
        System.out.println("==> 3 most popular tags and their posts titles");
        results.forEach(printDocuments());
    }

    private static Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }
}
