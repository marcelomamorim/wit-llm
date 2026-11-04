/*
 * @test
 * @summary Verify HashMap entry iterator next() returns entries and throws NoSuchElementException when exhausted
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyNext() {
        HashMap<String, String> map = new HashMap<>();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        check(!it.hasNext(), "empty map iterator should have no next");
        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        check(threw, "next() on empty map iterator must throw NoSuchElementException");
    }

    static void testSingleEntry() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        Map.Entry<String, Integer> e = it.next();
        check("a".equals(e.getKey()), "key mismatch");
        check(e.getValue() == 1, "value mismatch");
        check(!it.hasNext(), "single-entry map should be exhausted after one next()");
    }

    static void testIterateAll() {
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, "v" + i);
        }
        Set<Integer> seen = new HashSet<>();
        int count = 0;
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> e = it.next();
            check(e.getValue().equals("v" + e.getKey()), "entry key/value mismatch");
            seen.add(e.getKey());
            count++;
        }
        check(count == 100, "expected 100 entries, got " + count);
        check(seen.size() == 100, "expected 100 distinct keys");

        boolean threw = false;
        try {
            it.next();
        } catch (NoSuchElementException ex) {
            threw = true;
        }
        check(threw, "next() after exhaustion must throw NoSuchElementException");
    }

    static void testNullKeyAndValue() {
        HashMap<String, String> map = new HashMap<>();
        map.put(null, null);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> e = it.next();
        check(e.getKey() == null, "null key should be returned by next()");
        check(e.getValue() == null, "null value should be returned by next()");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testSetValueWritesThrough() {
        HashMap<String, String> map = new HashMap<>();
        map.put("k", "old");
        Map.Entry<String, String> e = map.entrySet().iterator().next();
        e.setValue("new");
        check("new".equals(map.get("k")), "setValue on entry from next() should write through to map");
    }

    public static void main(String[] args) {
        testEmptyNext();
        testSingleEntry();
        testIterateAll();
        testNullKeyAndValue();
        testSetValueWritesThrough();
    }
}
