/* @test    @summary Test Hashtable(Map<? extends K, ? extends V> t) constructor with various maps    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testSmallMap();
        testLargeMap();
        testNullValues();
        testNullKeys();
    }

    static void testEmptyMap() {
        Map<String, Integer> emptyMap = Collections.emptyMap();
        Hashtable<String, Integer> ht = new Hashtable<>(emptyMap);
        assert ht.size() == 0 : "Empty map should result in empty hashtable";
    }

    static void testSmallMap() {
        Map<String, String> smallMap = new HashMap<>();
        smallMap.put("a", "alpha");
        smallMap.put("b", "beta");
        Hashtable<String, String> ht = new Hashtable<>(smallMap);
        assert ht.size() == 2 : "Hashtable should contain 2 entries";
        assert "alpha".equals(ht.get("a")) : "Value for key 'a' should be 'alpha'";
        assert "beta".equals(ht.get("b")) : "Value for key 'b' should be 'beta'";
    }

    static void testLargeMap() {
        Map<Integer, String> largeMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            largeMap.put(i, "val" + i);
        }
        Hashtable<Integer, String> ht = new Hashtable<>(largeMap);
        assert ht.size() == 100 : "Hashtable should contain 100 entries";
        for (int i = 0; i < 100; i++) {
            assert "val" + i.equals(ht.get(i)) : "Value mismatch for key " + i;
        }
    }

    static void testNullValues() {
        Map<String, String> mapWithNulls = new HashMap<>();
        mapWithNulls.put("nullVal", null);
        try {
            new Hashtable<>(mapWithNulls);
            assert false : "Should throw NullPointerException for null value";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullKeys() {
        Map<String, String> mapWithNullKey = new HashMap<>();
        mapWithNullKey.put(null, "value");
        try {
            new Hashtable<>(mapWithNullKey);
            assert false : "Should throw NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
