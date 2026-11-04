/*
 * @test
 * @summary Observable behavior of TreeMap.containsKey via public API
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testContainsKeyAfterPutAndRemove();
        testNullValueStillContainsKey();
        testCaseInsensitiveComparator();
        testCopyConstructors();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMap() {
        TreeMap<String, Integer> map = new TreeMap<>();
        check(!map.containsKey("a"), "empty map must not contain key 'a'");
        check(!map.containsKey(""), "empty map must not contain empty string key");
    }

    static void testContainsKeyAfterPutAndRemove() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        check(map.containsKey("a"), "key 'a' should be present after put");
        check(map.containsKey("b"), "key 'b' should be present after put");
        check(!map.containsKey("c"), "key 'c' was never inserted");
        map.remove("a");
        check(!map.containsKey("a"), "key 'a' should be absent after remove");
        check(map.containsKey("b"), "key 'b' should still be present");
    }

    static void testNullValueStillContainsKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("k", null);
        check(map.containsKey("k"), "key mapped to null value must still be contained");
        check(map.get("k") == null, "get must return null for null-valued key");
    }

    static void testCaseInsensitiveComparator() {
        TreeMap<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("Hello", 1);
        check(map.containsKey("hello"), "comparator-equal key 'hello' should be found");
        check(map.containsKey("HELLO"), "comparator-equal key 'HELLO' should be found");
        check(!map.containsKey("world"), "non-equal key 'world' should not be found");
    }

    static void testCopyConstructors() {
        HashMap<String, Integer> source = new HashMap<>();
        source.put("x", 10);
        TreeMap<String, Integer> fromMap = new TreeMap<>(source);
        check(fromMap.containsKey("x"), "copy from Map must contain 'x'");
        check(!fromMap.containsKey("y"), "copy from Map must not contain 'y'");

        TreeMap<String, Integer> sortedSource = new TreeMap<>();
        sortedSource.put("p", 5);
        TreeMap<String, Integer> fromSorted = new TreeMap<>(sortedSource);
        check(fromSorted.containsKey("p"), "copy from SortedMap must contain 'p'");
        check(!fromSorted.containsKey("q"), "copy from SortedMap must not contain 'q'");
    }
}
