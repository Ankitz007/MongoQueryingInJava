import java.util.*;


public class segregation {
    ArrayList<String> query = new ArrayList<>();
    ArrayList<String> proj_q;
    public void cleaning(String s){
        for (String x : s.split(" "))
            query.add(x);

        // Getting order of Stages
        ArrayList<String> stage_index = new ArrayList<>();
        stage_index.add("sort");
        stage_index.add("match");
        stage_index.add("unwind");
        stage_index.add("project");
        stage_index.add("limit");
        stage_index.add("count");
        String[] x = s.split(" ");

        ArrayList<String> z = new ArrayList<>(Arrays.asList(x));
        z.removeAll(Collections.singleton("by"));
        z.removeAll(Collections.singleton("to"));
        Map<String, Integer> hm = new HashMap<>();
        for(int i=0; i<stage_index.size(); i++){
            if (z.contains(stage_index.get(i)))
                hm.put(stage_index.get(i), z.indexOf(stage_index.get(i)));
        }

        Map<String, Integer> hm1 = sortByValue(hm);
        Set<Map.Entry<String, Integer>> st
                = hm1.entrySet();
        ArrayList<String> query_stages_list = new ArrayList<>();
        for (Map.Entry<String, Integer> me : st) {
            query_stages_list.add(me.getKey());
        }
        proj_q = new ArrayList<>(z.subList(hm1.get("project") + 1, hm1.get(query_stages_list.get(query_stages_list.indexOf("project") + 1))));

    }

    public query_creation getQuery() {

        String s = "sort ascending by pop match state by NY project id pop zip city limit by 5";
        cleaning(s);
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
            fp = new function_project(proj_q);
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
    public static HashMap<String, Integer> sortByValue(Map<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
