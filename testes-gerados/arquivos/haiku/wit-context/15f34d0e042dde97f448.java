/*
 * @test
 * @summary Tests IdentityHashMap.forEach null action handling and basic observable behavior
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class IdentityHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachVisitsAllEntries();
        testForEachDistinguishesEqualKeys();
    }

    static void testNullActionThrowsNPE() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("a", "1");
        boolean thrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) should throw NullPointerException");
    }

    static void testForEachVisitsAllEntries() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        String k1 = new String("k");
        String k2 = new String("k");
        map.put(k1, 1);
        map.put(k2, 2);
        map.put(null, 3);
        List<Object> seen = new ArrayList<>();
        map.forEach((k, v) -> seen.add(v));
        check(seen.size() == 3, "expected 3 visited entries but got " + seen.size());
        check(seen.contains(1) && seen.contains(2) && seen.contains(3),
              "forEach should visit all values");
    }

    static void testForEachDistinguishesEqualKeys() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        String k1 = new String("same");
        String k2 = new String("same");
        map.put(k1, 10);
        map.put(k2, 20);
        check(map.size() == 2, "equal but distinct keys must both be stored");
        int[] count = new int[1];
        map.forEach((k, v) -> count[0]++);
        check(count[0] == 2, "forEach should visit both identity-distinct keys");
    }
}
