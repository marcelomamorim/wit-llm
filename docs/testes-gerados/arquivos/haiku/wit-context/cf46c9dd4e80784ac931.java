/*
 * @test
 * @summary Public entry setValue behavior of ConcurrentHashMap entries
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            Integer old = e.setValue(2);
            check(old.intValue() == 1);
        }
        check(map.get("a").intValue() == 2);
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }
}
