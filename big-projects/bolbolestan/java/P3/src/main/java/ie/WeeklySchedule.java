package ie;

import java.util.*;

public class WeeklySchedule {
    public List<Map<String, String>> codes = new ArrayList<Map<String, String>>();

    public boolean addNewCode(String code) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("status", "non-finalized");

        for (Iterator<Map<String, String>> iterator = this.codes.iterator(); iterator.hasNext(); ) {
            Map<String, String> m = iterator.next();
            if (m.containsValue(code)) {
                return false;
            }
        }
        this.codes.add(map);
        return true;
    }

    public void removeCode(String code) {
        for (Iterator<Map<String, String>> iterator = this.codes.iterator(); iterator.hasNext(); ) {
            Map<String, String> map = iterator.next();
            if (map.containsValue(code)) {
                iterator.remove();
            }
        }
    }
}
