/*
 * @test
 * @summary Verifies ConcurrentHashMap.keySet(V) rejects null mappedValue and returns a view that uses the given mapped value
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        boolean threw = false;
        try {
            map.keySet(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("keySet(null) should throw NullPointerException");
        }

        ConcurrentHashMap.KeySetView<String, Integer> view = map.keySet(Integer.valueOf(1));
        if (view == null) {
            throw new RuntimeException("keySet(non-null) returned null");
        }

        view.add("a");
        if (!map.containsKey("a")) {
            throw new RuntimeException("key added through keySet view not present in map");
        }
        if (map.get("a").intValue() != 1) {
            throw new RuntimeException("mapped value of key added through keySet view is wrong");
        }
    }
}
