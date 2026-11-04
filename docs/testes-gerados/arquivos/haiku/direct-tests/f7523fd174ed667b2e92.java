/*
 * @test
 * @summary Verify IdentityHashMap entry getValue returns the mapped value for entries from entrySet iteration, including null values and reference-distinct equal keys
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class IdentityHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsMappedValuesForReferenceDistinctEqualKeys() {
        IdentityHashMap<String, Integer> m = new IdentityHashMap<>();
        String k1 = new String("k");
        String k2 = new String("k");
        m.put(k1, Integer.valueOf(1));
        m.put(k2, Integer.valueOf(2));

        int matchedK1 = 0;
        int matchedK2 = 0;
        int total = 0;
        for (Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> e = it.next();
            total++;
            if (e.getKey() == k1) {
                check(Integer.valueOf(1).equals(e.getValue()), "k1 entry must return 1");
                matchedK1++;
            } else if (e.getKey() == k2) {
                check(Integer.valueOf(2).equals(e.getValue()), "k2 entry must return 2");
                matchedK2++;
            }
        }
        check(total == 2, "expected 2 entries but got " + total);
        check(matchedK1 == 1 && matchedK2 == 1, "each identity key must appear once");
    }

    static void testReturnsNullValue() {
        IdentityHashMap<Object, Object> m = new IdentityHashMap<>();
        Object key = new Object();
        m.put(key, null);

        Map.Entry<Object, Object> e = m.entrySet().iterator().next();
        check(e.getKey() == key, "key identity must be preserved");
        check(e.getValue() == null, "null value must be returned as null");
    }

    static void testValueMatchesGetAfterCopyConstructor() {
        IdentityHashMap<String, String> src = new IdentityHashMap<>();
        String k = new String("x");
        String v = new String("v");
        src.put(k, v);

        IdentityHashMap<String, String> copy = new IdentityHashMap<>(src);
        Map.Entry<String, String> e = copy.entrySet().iterator().next();
        check(e.getKey() == k, "copied key identity must be preserved");
        check(e.getValue() == v, "copied value must be the same reference");
        check(e.getValue() == copy.get(k), "entry value must match get()");
    }

    public static void main(String[] args) {
        testReturnsMappedValuesForReferenceDistinctEqualKeys();
        testReturnsNullValue();
        testValueMatchesGetAfterCopyConstructor();
    }
}
