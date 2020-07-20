import java.util.*;

public class cleaning_query {

    public ArrayList<String> clean(String s){

        // Getting order of Stages
        ArrayList<String> stage_index = new ArrayList<>();
        stage_index.add("sort");
        stage_index.add("match");
        stage_index.add("unwind");
        stage_index.add("project");
        stage_index.add("limit");
        stage_index.add("count");

        String[] str = s.split(" ");
        ArrayList<String> cleaned_query = new ArrayList<>(Arrays.asList(str));
        cleaned_query.removeAll(Collections.singleton("by"));
        cleaned_query.removeAll(Collections.singleton("to"));

        Map<String, Integer> hm = new HashMap<>();
        for(int i=0; i<stage_index.size(); i++){
            if (cleaned_query.contains(stage_index.get(i)))
                hm.put(stage_index.get(i), cleaned_query.indexOf(stage_index.get(i)));
        }

        Map<String, Integer> hm1 = sortByValue(hm);
        Set<Map.Entry<String, Integer>> st
                = hm1.entrySet();
        ArrayList<String> query_stages_list = new ArrayList<>();
        for (Map.Entry<String, Integer> me : st) {
            query_stages_list.add(me.getKey());
        }

        ArrayList<String> proj_q = new ArrayList<>(cleaned_query.subList(hm1.get("project") + 1, hm1.get(query_stages_list.get(query_stages_list.indexOf("project") + 1))));
        return proj_q;
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
