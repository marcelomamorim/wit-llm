/*
 * @test
 * @summary Public observable behavior of TreeMap.firstKey: empty map throws NoSuchElementException and non-empty map returns the least key
 * @run main TreeMapWitupTest
 */

import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMapThrowsNoSuchElement() {
        TreeMap<String, Integer> empty = new TreeMap<>();
        boolean thrown = false;
        try {
            empty.firstKey();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "firstKey on empty map must throw NoSuchElementException");
    }

    static void testReturnsLeastKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("b", 2);
        map.put("a", 1);
        map.put("c", 3);
        check("a".equals(map.firstKey()), "firstKey must return the least key");
    }

    static void testReturnsLeastKeyWithComparator() {
        TreeMap<String, Integer> map = new TreeMap<>(java.util.Comparator.reverseOrder());
        map.put("b", 2);
        map.put("a", 1);
        map.put("c", 3);
        check("c".equals(map.firstKey()), "firstKey must follow the comparator ordering");
    }

    public static void main(String[] args) {
        testEmptyMapThrowsNoSuchElement();
        testReturnsLeastKey();
        testReturnsLeastKeyWithComparator();
    }
}
