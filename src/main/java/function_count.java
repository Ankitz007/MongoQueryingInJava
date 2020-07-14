import org.bson.conversions.Bson;
import static com.mongodb.client.model.Aggregates.*;

public class function_count implements aggregate_stage {

    @Override
    public Bson generated_query() {
        Bson count = count();
        return count;
    }
}
