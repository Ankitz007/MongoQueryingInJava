import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class segregation {
    public static query_creation getQuery(MongoCollection<Document> zips) {
        String s = "sort ascending by pop match state by NY limit by 5";




        ArrayList<String> query = new ArrayList<String>();
        for (String x : s.split(" "))
            query.add(x);

        query_creation qc = new query_creation();

        function_sort fs = null;
        if (query.contains("sort")) {
            fs = new function_sort(query.get(query.indexOf("sort") + 1), query.get(query.indexOf("sort") + 3));
            qc.set_query(fs.generated_query());
        }
        function_match fm = null;
        if (query.contains("match")) {
            fm = new function_match(query.get(query.indexOf("match") + 1), query.get(query.indexOf("match") + 3));
            qc.set_query(fm.generated_query());
        }
        function_limit fl = null;
        if (query.contains("limit")) {
            fl = new function_limit(Integer.valueOf(query.get(query.indexOf("limit")+2)));
            qc.set_query(fl.generated_query());
        }
        function_project fp = null;
        if (query.contains("project")) {
            fp = new function_project();
        }
        function_unwind fu = null;
        if(query.contains("unwind")) {
            fu = new function_unwind(query.get(query.indexOf("unwind")+2));
        }
        return qc;
    }

}
