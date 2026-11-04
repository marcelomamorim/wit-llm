/*
 * @test    @summary Test ConcurrentHashMap constructor with a non-empty map
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithMap();
        testConstructorWithEmptyMap();
    }

    static void testConstructorWithMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("one", 1);
        baseMap.put("two", 2);
        ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>(baseMap);
        assert chm.size() == 2 : "Size should be 2";
        assert chm.get("one") == 1 : "Value for 'one' should be 1";
        assert chm.get("two") == 2 : "Value for 'two'";
    }

    static void testConstructorWithEmptyMap() {
        Map<String, String> emptyMap = new HashMap<>();
        ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>(emptyMap);
        assert chm.size() == 0 : "Size should be 0";
        assert chm.isEmpty() : "Map should be empty";
    }
}
