import org.bson.conversions.Bson;

import static com.mongodb.client.model.Aggregates.limit;

public class function_limit {
    int limit;
    function_limit(int n){
        limit = n;
    }
    public Bson generated_query_from_limit(){
        Bson limit_stage = limit(limit);
        return limit_stage;
    }
}
