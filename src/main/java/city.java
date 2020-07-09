import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Sorts.descending;

public class city {
    public static void threeMostPopulatedCitiesInTexas(MongoCollection<Document> zips) {
        Bson match = match(eq("state", "TX"));
        Bson group = group("$city", sum("totalPop", "$pop"));
        Bson project = project(fields(excludeId(), include("totalPop"), computed("city", "$_id")));
        Bson sort = sort(descending("totalPop"));
        Bson limit = limit(3);

        List<Document> results = zips.aggregate(Arrays.asList(match, group, project, sort, limit))
                .into(new ArrayList<>());
        System.out.println("==> 3 most densely populated cities in Texas");
        results.forEach(printDocuments());
    }
    private static Consumer<Document> printDocuments() {
        return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
    }
}
