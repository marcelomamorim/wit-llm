/*
 * @test
 * @summary Verifies HashMap key iterator next() returns every key, including null, and values/entries via public views
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testKeyIteratorReturnsAllKeys();
        testNullKeyReturnedByNext();
        testValuesIteratorReturnsAllValues();
        testEntryIteratorNextReturnsMapping();
        testSingleElementKeyIterator();
        testCopyConstructorKeyIterator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testKeyIteratorReturnsAllKeys() {
        HashMap<Integer, String> m = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            m.put(i, "v" + i);
        }
        Iterator<Integer> it = m.keySet().iterator();
        TreeSet<Integer> seen = new TreeSet<>();
        int count = 0;
        while (it.hasNext()) {
            seen.add(it.next());
            count++;
        }
        check(count == 100, "expected 100 next() calls, got " + count);
        check(seen.size() == 100, "expected 100 distinct keys");
        check(seen.first() == 0 && seen.last() == 99, "unexpected key range");
    }

    static void testNullKeyReturnedByNext() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put(null, 42);
        Iterator<String> it = m.keySet().iterator();
        check(it.hasNext(), "iterator should have one element");
        String k = it.next();
        check(k == null, "next() should return the null key");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testValuesIteratorReturnsAllValues() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", null);
        Iterator<Integer> it = m.values().iterator();
        TreeSet<Integer> seen = new TreeSet<>();
        boolean sawNull = false;
        while (it.hasNext()) {
            Integer v = it.next();
            if (v == null) {
                sawNull = true;
            } else {
                seen.add(v);
            }
        }
        check(sawNull, "null value should be returned by next()");
        check(seen.size() == 2 && seen.first() == 1 && seen.last() == 2, "unexpected values");
    }

    static void testEntryIteratorNextReturnsMapping() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("x", 5);
        Map.Entry<String, Integer> e = m.entrySet().iterator().next();
        check("x".equals(e.getKey()), "entry key mismatch");
        check(e.getValue() == 5, "entry value mismatch");
    }

    static void testSingleElementKeyIterator() {
        HashMap<String, String> m = new HashMap<>();
        m.put("only", "value");
        Iterator<String> it = m.keySet().iterator();
        check(it.hasNext(), "expected one element");
        check("only".equals(it.next()), "wrong single key");
        check(!it.hasNext(), "expected no more elements");
    }

    static void testCopyConstructorKeyIterator() {
        HashMap<String, Integer> src = new HashMap<>();
        src.put("p", 1);
        src.put("q", 2);
        HashMap<String, Integer> copy = new HashMap<>(src);
        Iterator<String> it = copy.keySet().iterator();
        TreeSet<String> seen = new TreeSet<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.size() == 2 && seen.contains("p") && seen.contains("q"), "copied keys mismatch");
    }
}
