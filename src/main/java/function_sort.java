import org.bson.conversions.Bson;

import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

public class function_sort {
    String sort_type;
    String sort_field;
    function_sort(String s_f){
    sort_type = "ascending";
    sort_field = s_f;
    }
    function_sort(String s_t, String s_f){
    sort_type = s_t;
    sort_field = s_f;
    }
    public Bson generated_query_from_sort(){
        Bson sort_stage = sort(ascending(sort_field));;
        if(sort_type.equals("descending")){
            sort_stage = sort(descending(sort_field));
            return sort_stage;
        }
        return sort_stage;
    }
}
