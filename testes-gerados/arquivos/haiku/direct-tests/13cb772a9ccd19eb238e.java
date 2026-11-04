/*
 * @test
 * @summary Verifies that IdentityHashMap key iteration via next() returns keys by reference identity, including null and equal-but-distinct keys
 * @run main IdentityHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testIdenticalKeysAreReturnedByReference();
        testNullKeyIsReturnedAsNull();
        testAllKeysReturnedExactlyOnce();
    }

    static void testIdenticalKeysAreReturnedByReference() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String a = new String("k");
        String b = new String("k");
        map.put(a, "1");
        map.put(b, "2");
        check(map.size() == 2, "size should be 2 for distinct equal keys");

        List<String> seen = collectKeys(map);
        check(seen.size() == 2, "iterator should yield 2 keys");
        boolean hasA = false;
        boolean hasB = false;
        for (String s : seen) {
            if (s == a) hasA = true;
            if (s == b) hasB = true;
        }
        check(hasA && hasB, "iterator must return both distinct key references");
    }

    static void testNullKeyIsReturnedAsNull() {
        IdentityHashMap<Object, String> map = new IdentityHashMap<>();
        map.put(null, "n");
        Object other = new Object();
        map.put(other, "o");

        List<Object> seen = new ArrayList<>();
        Iterator<Object> it = map.keySet().iterator();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.size() == 2, "iterator should yield 2 keys including null");
        int nulls = 0;
        boolean hasOther = false;
        for (Object o : seen) {
            if (o == null) nulls++;
            if (o == other) hasOther = true;
        }
        check(nulls == 1, "null key must be returned as null exactly once");
        check(hasOther, "non-null key must be returned by reference");
    }

    static void testAllKeysReturnedExactlyOnce() {
        IdentityHashMap<Integer, String> map = new IdentityHashMap<>();
        Integer[] keys = new Integer[50];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new Integer(1000 + i);
            map.put(keys[i], "v" + i);
        }
        List<Integer> seen = new ArrayList<>();
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.size() == keys.length, "iterator must yield every key once");
        for (Integer k : keys) {
            int count = 0;
            for (Integer s : seen) {
                if (s == k) count++;
            }
            check(count == 1, "each key reference must appear exactly once");
        }
    }

    static List<String> collectKeys(Map<String, String> map) {
        List<String> result = new ArrayList<>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
