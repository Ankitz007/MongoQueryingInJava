import org.bson.conversions.Bson;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;

public class function_match {
    String match_field;
    String field_name;
    String match_operator;
    function_match(String m_f, String f_n) {
        match_field = m_f;
        field_name = f_n;
        match_operator = "eq";
    }
    function_match(String m_f, String f_n, String m_o) {
        match_field = m_f;
        match_operator = m_o;
        field_name = f_n;
    }
    public Bson generated_query_from_match() {
        Bson match_stage = match(eq(match_field, field_name));
        if(match_operator.equals("gte")){
            match_stage = match(gte(match_field, field_name));
        }
        if(match_operator.equals("lte")){
            match_stage = match(lte(match_field, field_name));
        }
        if(match_operator.equals("gt")){
            match_stage = match(gt(match_field, field_name));
        }
        if(match_operator.equals("lt")){
            match_stage = match(lt(match_field, field_name));
        }
        return match_stage;
    }
}
