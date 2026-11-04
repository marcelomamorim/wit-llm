/*
 * @test
 * @summary Tests the Hashtable(Map) copy constructor: copied entries, empty map, independence from source map, and NullPointerException cases
 * @run main HashtableWitupTest
 */

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testCopiesAllEntries();
        testEmptySourceMap();
        testCopyIsIndependentOfSource();
        testNullSourceMapThrowsNPE();
        testNullValueInSourceThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesAllEntries() {
        Map<String, Integer> source = new HashMap<>();
        source.put("one", 1);
        source.put("two", 2);
        source.put("three", 3);

        Hashtable<String, Integer> copy = new Hashtable<>(source);

        check(copy.size() == 3, "size expected 3 but was " + copy.size());
        check(copy.get("one") == 1, "value for one");
        check(copy.get("two") == 2, "value for two");
        check(copy.get("three") == 3, "value for three");
        check(copy.containsKey("two"), "containsKey two");
        check(!copy.containsKey("four"), "unexpected key four");
    }

    static void testEmptySourceMap() {
        Hashtable<String, String> copy = new Hashtable<>(new HashMap<String, String>());

        check(copy.isEmpty(), "copy of empty map must be empty");
        check(copy.size() == 0, "size of copy of empty map must be 0");

        copy.put("k", "v");
        check("v".equals(copy.get("k")), "copy must remain usable after construction");
    }

    static void testCopyIsIndependentOfSource() {
        Map<Integer, String> source = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            source.put(i, "v" + i);
        }

        Hashtable<Integer, String> copy = new Hashtable<>(source);
        check(copy.size() == 100, "size expected 100 but was " + copy.size());
        for (int i = 0; i < 100; i++) {
            check(("v" + i).equals(copy.get(i)), "entry " + i + " missing or wrong");
        }

        source.put(1000, "new");
        source.remove(0);
        check(!copy.containsKey(1000), "adding to source must not affect copy");
        check(copy.containsKey(0), "removing from source must not affect copy");

        copy.put(2000, "copyOnly");
        check(!source.containsKey(2000), "adding to copy must not affect source");
    }

    static void testNullSourceMapThrowsNPE() {
        boolean thrown = false;
        try {
            new Hashtable<String, String>((Map<String, String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null map must throw NullPointerException");
    }

    static void testNullValueInSourceThrowsNPE() {
        Map<String, String> source = new HashMap<>();
        source.put("key", null);

        boolean thrown = false;
        try {
            new Hashtable<String, String>(source);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null value in source map must throw NullPointerException");
    }
}
