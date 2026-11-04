/*
 * @test
 * @summary Verifies observable behavior of TreeMap.entrySet() through the public Map/Set API
 * @run main TreeMapWitupTest
 */

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapEntrySet();
        testEntrySetIteratesInSortedOrder();
        testEntrySetReflectsRemoval();
        testEntrySetValueWritesThrough();
    }

    static void testEmptyMapEntrySet() {
        TreeMap<String, Integer> m = new TreeMap<>();
        Set<Map.Entry<String, Integer>> es = m.entrySet();
        check(es != null, "entrySet must not be null");
        check(es.isEmpty(), "entrySet of empty map must be empty");
        check(es.size() == 0, "size of empty entrySet must be 0");
        check(!es.iterator().hasNext(), "iterator of empty entrySet must be exhausted");
    }

    static void testEntrySetIteratesInSortedOrder() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("b", 2);
        m.put("a", 1);
        m.put("c", 3);
        Set<Map.Entry<String, Integer>> es = m.entrySet();
        check(es.size() == 3, "size must be 3");
        Iterator<Map.Entry<String, Integer>> it = es.iterator();
        check(it.hasNext(), "iterator must have first element");
        Map.Entry<String, Integer> e1 = it.next();
        check(e1.getKey().equals("a") && e1.getValue().intValue() == 1, "first entry must be a=1");
        Map.Entry<String, Integer> e2 = it.next();
        check(e2.getKey().equals("b") && e2.getValue().intValue() == 2, "second entry must be b=2");
        Map.Entry<String, Integer> e3 = it.next();
        check(e3.getKey().equals("c") && e3.getValue().intValue() == 3, "third entry must be c=3");
        check(!it.hasNext(), "iterator must be exhausted");
    }

    static void testEntrySetReflectsRemoval() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Set<Map.Entry<String, Integer>> es = m.entrySet();
        Iterator<Map.Entry<String, Integer>> it = es.iterator();
        it.next();
        it.remove();
        check(m.size() == 2, "map size must be 2 after iterator removal");
        check(!m.containsKey("a"), "removed key must no longer be in map");
        check(es.size() == 2, "entrySet view must reflect removal");
        m.put("d", 4);
        check(es.size() == 3, "entrySet view must reflect insertion");
    }

    static void testEntrySetValueWritesThrough() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("b", 2);
        m.put("a", 1);
        for (Map.Entry<String, Integer> e : m.entrySet()) {
            if (e.getKey().equals("b")) {
                e.setValue(20);
            }
        }
        check(m.get("b").intValue() == 20, "setValue on entry must write through to map");
        check(m.get("a").intValue() == 1, "unrelated value must be unchanged");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
