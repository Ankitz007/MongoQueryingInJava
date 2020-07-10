import org.bson.conversions.Bson;

import static com.mongodb.client.model.Aggregates.unwind;

public class function_unwind {
    String field_name;
    function_unwind(String f_n){
        field_name = f_n;
    }
    public Bson generated_query_from_unwind(){
        Bson unwind = unwind(field_name);
        return unwind;
    }

}
