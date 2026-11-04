/* @test    @summary Test for WeakHashMap constructor with Map parameter    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.HashMap;
import java.util.Map;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithMap();
        testConstructorWithNullMap();
        testConstructorWithEmptyMap();
    }

    private static void testConstructorWithMap() {
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put("key1", "value1");
        sourceMap.put("key2", "value2");
        WeakHashMap<String, String> whm = new WeakHashMap<>(sourceMap);
        assert whm.size() == 2 : "Expected size 2, but got " + whm.size();
        assert whm.get("key1").equals("value1") : "Value mismatch for key1";
        assert whm.get("key2").equals("value2") : "Value mismatch for key2";
    }

    private static void testConstructorWithNullMap() {
        try {
            WeakHashMap<String, String> whm = new WeakHashMap<>(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testConstructorWithEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        WeakHashMap<String, String> whm = new WeakHashMap<>(emptyMap);
        assert whm.size() == 0 : "Expected size 0, but got " + whm.size();
    }
}
