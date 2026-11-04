/*
 * @test
 * @summary Checks observable behavior of TreeMap.replace(K, V) for present keys, absent keys, null values and a custom comparator
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");

        check("old value of present key", "a", m.replace(1, "x"));
        check("value updated for present key", "x", m.get(1));
        check("replace of absent key returns null", null, m.replace(3, "c"));
        check("absent key not inserted", Boolean.FALSE, m.containsKey(3));
        check("size unchanged after absent replace", 2, m.size());
        check("null value replaces present value", "b", m.replace(2, null));
        check("key still present after null value", Boolean.TRUE, m.containsKey(2));
        check("get returns null after null value", null, m.get(2));

        TreeMap<String, Integer> r = new TreeMap<>(Comparator.reverseOrder());
        r.put("k", 1);
        check("comparator map old value", 1, r.replace("k", 2));
        check("comparator map updated value", 2, r.get("k"));
        check("comparator map absent key returns null", null, r.replace("z", 3));
        check("comparator map size unchanged", 1, r.size());
    }

    static void check(String name, Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new RuntimeException(name + ": expected " + expected + " but was " + actual);
        }
    }
}
