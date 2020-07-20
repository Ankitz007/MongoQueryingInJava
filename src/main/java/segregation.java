import java.util.*;


public class segregation {
    ArrayList<String> query = new ArrayList<>();

    public query_creation getQuery() {

        String s = "sort ascending by pop match state by NY project id pop zip city limit by 5";
        for (String str : s.split(" "))
            query.add(str);
        cleaning_query cleaning = new cleaning_query();
        ArrayList<String> proj_params = cleaning.clean(s);
        query_creation qc = new query_creation();

        function_sort fs;
        if (query.contains("sort")) {
            fs = new function_sort(query.get(query.indexOf("sort") + 1), query.get(query.indexOf("sort") + 3));
            qc.set_query(fs.generated_query());
        }
        function_match fm;
        if (query.contains("match")) {
            fm = new function_match(query.get(query.indexOf("match") + 1), query.get(query.indexOf("match") + 3));
            qc.set_query(fm.generated_query());
        }
        function_limit fl;
        if (query.contains("limit")) {
            fl = new function_limit(Integer.valueOf(query.get(query.indexOf("limit")+2)));
            qc.set_query(fl.generated_query());
        }
        function_project fp;
        if (query.contains("project")) {
            fp = new function_project(proj_params);
            qc.set_query(fp.generated_query());
        }
        function_count fc;
        if (query.contains("count")) {
            fc = new function_count();
            qc.set_query(fc.generated_query());
        }
        function_unwind fu = null;
        if(query.contains("unwind")) {
            fu = new function_unwind(query.get(query.indexOf("unwind")+2));
        }
        return qc;
    }

}
