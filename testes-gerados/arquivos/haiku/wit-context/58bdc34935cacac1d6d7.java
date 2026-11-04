/*
 * @test
 * @summary Witup-generated tests for TreeMap.containsKey
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testPresentAndAbsentKeys();
        testNullKeyNaturalOrderingThrowsNPE();
        testNullKeyWithNullsFirstComparator();
        testCaseInsensitiveComparator();
        testCopyConstructor();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMap() {
        TreeMap<String, Integer> map = new TreeMap<>();
        check(!map.containsKey("a"), "empty map must not contain key");
    }

    static void testPresentAndAbsentKeys() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        check(map.containsKey("a"), "key a must be present");
        check(!map.containsKey("b"), "key b must be absent");
    }

    static void testNullKeyNaturalOrderingThrowsNPE() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        try {
            map.containsKey(null);
            throw new RuntimeException("expected NullPointerException for null key with natural ordering");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullKeyWithNullsFirstComparator() {
        Comparator<String> cmp = Comparator.nullsFirst(Comparator.<String>naturalOrder());
        TreeMap<String, Integer> map = new TreeMap<>(cmp);
        map.put(null, 1);
        map.put("a", 2);
        check(map.containsKey(null), "null key must be found when comparator accepts null");
        check(map.containsKey("a"), "key a must be found");
        check(!map.containsKey("b"), "key b must be absent");
    }

    static void testCaseInsensitiveComparator() {
        TreeMap<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("a", 1);
        check(map.containsKey("A"), "case-insensitive comparator must match A with a");
        check(!map.containsKey("B"), "B must be absent");
    }

    static void testCopyConstructor() {
        Map<String, Integer> source = new HashMap<>();
        source.put("x", 10);
        source.put("y", 20);
        TreeMap<String, Integer> map = new TreeMap<>(source);
        check(map.containsKey("x"), "copied key x must be present");
        check(map.containsKey("y"), "copied key y must be present");
        check(!map.containsKey("z"), "key z must be absent");
    }
}
