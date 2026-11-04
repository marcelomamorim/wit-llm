/*
 * @test
 * @summary Test for ConcurrentHashMap constructor with Map parameter
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithMap();
    }

    private static void testConstructorWithMap() {
        Map<String, Integer> initialMap = Map.of("a", 1, "b", 2);
        ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>(initialMap);
        if (chm == null) {
            throw new AssertionError("Constructor returned null");
        }
        if (chm.size() != initialMap.size()) {
            throw new AssertionError("Size mismatch: expected " + initialMap.size() + " but got " + chm.size());
        }
        for (Map.Entry<String, Integer> entry : initialMap.entrySet()) {
            Integer value = chm.get(entry.getKey());
            if (value == null || !value.equals(entry.getValue())) {
                throw new AssertionError("Value mismatch for key " + entry.getKey());
            }
        }
    }
}
