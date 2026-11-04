/*
 * @test
 * @summary Test WeakHashMap constructor with a Map argument
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.HashMap;
import java.util.Map;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithEmptyMap();
        testConstructorWithPrepopulatedMap();
    }

    static void testConstructorWithEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        WeakHashMap<String, String> whm = new WeakHashMap<>(emptyMap);
        assert whm.size() == 0 : "Expected size 0 for empty map";
        System.out.println("testConstructorWithEmptyMap passed.");
    }

    static void testConstructorWithPrepopulatedMap() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        WeakHashMap<String, String> whm = new WeakHashMap<>(map);
        assert whm.size() == 2 : "Expected size 2 for prepopulated map";
        assert whm.get("key1").equals("value1") : "Value mismatch for key1";
        assert whm.get("key2").equals("value2") : "Value mismatch for key2";
        System.out.println("testConstructorWithPrepopulatedMap passed.");
    }
}
