/*
 * @test
 * @summary WitUp generated checks for WeakHashMap entrySet toArray(T[]) through the public API
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkContents(Object[] arr, WeakHashMap<String, Integer> m, int expectedCount) {
        int found = 0;
        for (Object o : arr) {
            if (o == null) {
                continue;
            }
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            check(m.containsKey(e.getKey()), "unexpected key in array: " + e.getKey());
            check(e.getValue().equals(m.get(e.getKey())), "value mismatch for key " + e.getKey());
            found++;
        }
        check(found == expectedCount, "expected " + expectedCount + " entries but found " + found);
    }

    public static void main(String[] args) {
        String[] keys = {"a", "b", "c"};
        WeakHashMap<String, Integer> m = new WeakHashMap<>();
        for (int i = 0; i < keys.length; i++) {
            m.put(keys[i], i + 1);
        }
        Set<Map.Entry<String, Integer>> es = m.entrySet();

        Object[] arr = es.toArray(new Map.Entry[0]);
        check(arr.length == 3, "length with empty array argument: " + arr.length);
        checkContents(arr, m, 3);

        Object[] exact = es.toArray(new Map.Entry[3]);
        check(exact.length == 3, "exact-size array length: " + exact.length);
        checkContents(exact, m, 3);

        Object[] big = new Map.Entry[5];
        Object[] bigResult = es.toArray(big);
        check(bigResult == big, "larger array should be returned as the same instance");
        check(big[3] == null, "element after size should be null");
        check(big[4] == null, "element after size should be null");
        checkContents(big, m, 3);

        WeakHashMap<String, Integer> empty = new WeakHashMap<>();
        Object[] emptyArr = empty.entrySet().toArray(new Map.Entry[5]);
        check(emptyArr.length == 5, "empty map result length: " + emptyArr.length);
        check(emptyArr[0] == null, "empty map first element should be null");
        Object[] emptyNew = empty.entrySet().toArray(new Map.Entry[0]);
        check(emptyNew.length == 0, "empty map should yield zero-length array");

        boolean npe = false;
        try {
            es.toArray((Map.Entry[]) null);
        } catch (NullPointerException ex) {
            npe = true;
        }
        check(npe, "null array argument should throw NullPointerException");

        boolean ase = false;
        try {
            es.toArray(new String[0]);
        } catch (ArrayStoreException ex) {
            ase = true;
        }
        check(ase, "incompatible array type should throw ArrayStoreException");
    }
}
