/*
 * @test
 * @summary Verifies HashMap.HashIterator.next() through the public values() view
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapValuesIteratorHasNoNext();
        testSingleNullValueIsReturned();
        testNextReturnsAllValuesOnce();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMapValuesIteratorHasNoNext() {
        HashMap<String, Integer> map = new HashMap<>();
        Iterator<Integer> it = map.values().iterator();
        check(!it.hasNext(), "empty map iterator must have no elements");
    }

    static void testSingleNullValueIsReturned() {
        HashMap<String, String> map = new HashMap<>();
        map.put("k", null);
        Iterator<String> it = map.values().iterator();
        check(it.hasNext(), "iterator must have one element");
        String value = it.next();
        check(value == null, "next() must return the null value");
        check(!it.hasNext(), "iterator must be exhausted after one element");
    }

    static void testNextReturnsAllValuesOnce() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Set<Integer> seen = new HashSet<>();
        int count = 0;
        Iterator<Integer> it = map.values().iterator();
        while (it.hasNext()) {
            seen.add(it.next());
            count++;
        }
        check(count == 3, "next() must be invoked once per mapping, got " + count);
        check(seen.size() == 3, "all values must be distinct and returned");
        check(seen.contains(1) && seen.contains(2) && seen.contains(3),
              "next() must return exactly the stored values");
    }
}
