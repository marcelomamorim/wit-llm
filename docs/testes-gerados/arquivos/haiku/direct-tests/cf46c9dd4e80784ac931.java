/*
 * @test
 * @summary Checks write-through setValue behavior of entries obtained from ConcurrentHashMap.entrySet()
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSetValueWritesThrough() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("k", 1);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        Map.Entry<String, Integer> e = it.next();
        check("k".equals(e.getKey()), "unexpected key");
        Integer old = e.setValue(5);
        check(old != null && old == 1, "setValue should return previous value 1");
        check(map.get("k") == 5, "setValue should write through to the map");
    }

    static void testSetValueReturnsPreviousAfterRepeatedCalls() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 10);
        Map.Entry<String, Integer> e = map.entrySet().iterator().next();
        check(e.setValue(20) == 10, "first setValue should return 10");
        check(e.setValue(30) == 20, "second setValue should return 20");
        check(map.get("a") == 30, "map should hold last assigned value");
    }

    static void testSetValueOnSingleEntryMapOnly() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(7, "x");
        Map.Entry<Integer, String> e = map.entrySet().iterator().next();
        check("x".equals(e.setValue("y")), "setValue should return \"x\"");
        check("y".equals(map.get(7)), "map should reflect new value \"y\"");
        check(map.size() == 1, "setValue must not change map size");
    }

    public static void main(String[] args) {
        testSetValueWritesThrough();
        testSetValueReturnsPreviousAfterRepeatedCalls();
        testSetValueOnSingleEntryMapOnly();
    }
}
