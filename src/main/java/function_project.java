import org.bson.conversions.Bson;
import java.util.ArrayList;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.*;

public class function_project implements aggregate_stage{

    ArrayList<String> project_fields;
    function_project(ArrayList<String> pfs){
        project_fields = new ArrayList<>(pfs);
    }

    @Override
    public Bson generated_query() {
        Bson project = project(include(project_fields));
        if(!project_fields.contains("id")){
            project = project(fields(excludeId(), include(project_fields)));
        }
        return project;
    }
}
