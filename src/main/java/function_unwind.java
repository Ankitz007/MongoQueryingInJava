import org.bson.conversions.Bson;

import static com.mongodb.client.model.Aggregates.unwind;

public class function_unwind implements aggregate_stage {
    String field_name;
    function_unwind(String f_n){
        field_name = f_n;
    }

    @Override
    public Bson generated_query() {
        Bson unwind = unwind(field_name);
        return unwind;
    }
}
