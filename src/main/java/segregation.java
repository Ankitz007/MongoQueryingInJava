import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.*;
import java.util.function.Consumer;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class segregation {
    ArrayList<String> query = new ArrayList<String>();
    ArrayList<String> zain;
    public void cleaning(){
        String s = "sort ascending by pop match state by NY project pop zip city limit by 5";


        for (String x : s.split(" "))
            query.add(x);

        // Getting order of Stages
        ArrayList<String> ank = new ArrayList<String>();
        ank.add("sort");
        ank.add("match");
        ank.add("unwind");
        ank.add("project");
        ank.add("limit");
        ank.add("count");
        String[] x = s.split(" ");

        ArrayList<String> z = new ArrayList<String>(Arrays.asList(x));
        z.removeAll(Collections.singleton("by"));
        z.removeAll(Collections.singleton("to"));

        Map<String, Integer> hm = new HashMap<String, Integer>();
        if (z.contains(ank.get(0)))
            hm.put("sort", z.indexOf("sort"));
        if (z.contains(ank.get(1)))
            hm.put("match", z.indexOf("match"));
        if (z.contains(ank.get(2)))
            hm.put("unwind", z.indexOf("unwind"));
        if (z.contains(ank.get(3)))
            hm.put("project", z.indexOf("project"));
        if (z.contains(ank.get(4)))
            hm.put("limit", z.indexOf("limit"));
        if (z.contains(ank.get(5)))
            hm.put("count", z.indexOf("count"));

        Map<String, Integer> hm1 = sortByValue(hm);
        Set<Map.Entry<String, Integer>> st
                = hm1.entrySet();
        ArrayList<String> query_stages_list = new ArrayList<String>();
        for (Map.Entry<String, Integer> me : st) {
            query_stages_list.add(me.getKey());
        }
        zain = new ArrayList<String>(z.subList(hm1.get("project") + 1, hm1.get(query_stages_list.get(query_stages_list.indexOf("project")+1))));

    }

    public query_creation getQuery(MongoCollection<Document> zips) {

        cleaning();
        query_creation qc = new query_creation();

        function_sort fs = null;
        if (query.contains("sort")) {
            fs = new function_sort(query.get(query.indexOf("sort") + 1), query.get(query.indexOf("sort") + 3));
            qc.set_query(fs.generated_query());
        }
        function_match fm = null;
        if (query.contains("match")) {
            fm = new function_match(query.get(query.indexOf("match") + 1), query.get(query.indexOf("match") + 3));
            qc.set_query(fm.generated_query());
        }
        function_limit fl = null;
        if (query.contains("limit")) {
            fl = new function_limit(Integer.valueOf(query.get(query.indexOf("limit")+2)));
            qc.set_query(fl.generated_query());
        }
        function_project fp = null;
        if (query.contains("project")) {
            fp = new function_project(zain);
            qc.set_query(fp.generated_query());
        }
        function_count fc = null;
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
    public static HashMap<String, Integer> sortByValue(Map<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
