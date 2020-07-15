import org.bson.conversions.Bson;

import java.util.ArrayList;

import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.fields;

public class function_project implements aggregate_stage{

    ArrayList<String> project_fields;
    function_project(ArrayList<String> pfs){
        project_fields = pfs;
    }
    ArrayList<String> m = new ArrayList<String>(project_fields);

    public void field_formation(){
        for(int i=0;i<m.size();i++){
            m.set(i, "\""+m.get(i)+"\"");
        }
    }
    String a2s = String.join(", ", m);
    @Override
    public Bson generated_query() {
        Bson project = project(fields());
        return project;
    }
}
