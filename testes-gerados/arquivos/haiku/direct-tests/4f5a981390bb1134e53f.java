/*
 * @test
 * @summary Verifies HashMap iterator remove() behavior through public keySet, entrySet and values iterators
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testRemoveAfterNextRemovesMapping();
        testRemoveWithoutNextThrowsIllegalState();
        testRemoveTwiceThrowsIllegalState();
        testRemoveViaEntrySetIterator();
        testRemoveViaValuesIterator();
        testRemoveAfterStructuralModificationThrowsCME();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static HashMap<String, Integer> sampleMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        return map;
    }

    private static void testRemoveAfterNextRemovesMapping() {
        HashMap<String, Integer> map = sampleMap();
        Iterator<String> it = map.keySet().iterator();
        String removed = it.next();
        it.remove();
        check(!map.containsKey(removed), "removed key still present: " + removed);
        check(map.size() == 2, "unexpected size after remove: " + map.size());
        for (String k : new String[] {"a", "b", "c"}) {
            if (!k.equals(removed)) {
                check(map.containsKey(k), "other key lost: " + k);
            }
        }
    }

    private static void testRemoveWithoutNextThrowsIllegalState() {
        HashMap<String, Integer> map = sampleMap();
        Iterator<String> it = map.keySet().iterator();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(map.size() == 3, "map changed by failed remove");
    }

    private static void testRemoveTwiceThrowsIllegalState() {
        HashMap<String, Integer> map = sampleMap();
        Iterator<String> it = map.keySet().iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(map.size() == 2, "unexpected size after double remove: " + map.size());
    }

    private static void testRemoveViaEntrySetIterator() {
        HashMap<String, Integer> map = sampleMap();
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        Map.Entry<String, Integer> e = it.next();
        String key = e.getKey();
        it.remove();
        check(!map.containsKey(key), "entry key still present: " + key);
        check(map.size() == 2, "unexpected size after entry remove");
    }

    private static void testRemoveViaValuesIterator() {
        HashMap<String, Integer> map = sampleMap();
        Iterator<Integer> it = map.values().iterator();
        it.next();
        it.remove();
        check(map.size() == 2, "unexpected size after values remove");
        check(map.values().size() == 2, "values view inconsistent");
    }

    private static void testRemoveAfterStructuralModificationThrowsCME() {
        HashMap<String, Integer> map = sampleMap();
        Iterator<String> it = map.keySet().iterator();
        it.next();
        map.put("zz-new-key", 99);
        try {
            it.remove();
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
