/*
 * @test
 * @summary Test Map.comparingByValue static method with a sample comparator
 * @run main MapWitupTest
 */

import java.util.Map;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.io.Serializable;
import java.util.Objects;

public class MapWitupTest {
    public static void main(String[] args) {
        // Test that passing null comparator throws NullPointerException
        try {
            Map.comparingByValue(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
        }

        // Create a comparator for Integer values
        Comparator<Integer> intCmp = new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        };

        // Get the comparator returned by comparingByValue
        Comparator<Map.Entry<String, Integer>> entryCmp = Map.comparingByValue(intCmp);

        // Create two entries with different values
        Map.Entry<String, Integer> e1 = new AbstractMap.SimpleEntry<>("a", 1);
        Map.Entry<String, Integer> e2 = new AbstractMap.SimpleEntry<>("b", 2);

        // Compare entries: e1 < e2 expected
        int res1 = entryCmp.compare(e1, e2);
        if (res1 >= 0) {
            throw new RuntimeException("Comparator failed: expected e1 < e2");
        }

        // Compare entries: e2 > e1 expected
        int res2 = entryCmp.compare(e2, e1);
        if (res2 <= 0) {
            throw new RuntimeException("Comparator failed: expected e2 > e1");
        }

        // Compare entries with equal values
        Map.Entry<String, Integer> e3 = new AbstractMap.SimpleEntry<>("c", 1);
        int res3 = entryCmp.compare(e1, e3);
        if (res3 != 0) {
            throw new RuntimeException("Comparator failed: expected e1 == e3");
        }

        // Test that the comparator is Serializable
        if (!(entryCmp instanceof Serializable)) {
            throw new RuntimeException("Returned comparator is not Serializable");
        }
    }
}
