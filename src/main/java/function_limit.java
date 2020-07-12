import org.bson.conversions.Bson;

import static com.mongodb.client.model.Aggregates.limit;

public class function_limit implements aggregate_stage{
    int limit;
    function_limit(int n){
        limit = n;
    }

    @Override
    public Bson generated_query(){
        Bson limit_stage = limit(limit);
        return limit_stage;
    }
}
