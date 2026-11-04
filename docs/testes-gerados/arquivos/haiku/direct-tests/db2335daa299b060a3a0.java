/*
 * @test
 * @summary Verify TreeMap.comparator() returns the comparator supplied at construction, and null for natural ordering
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        Comparator<Integer> rev = Comparator.reverseOrder();

        TreeMap<Integer, Integer> withCmp = new TreeMap<>(rev);
        if (withCmp.comparator() != rev) {
            throw new RuntimeException("comparator() did not return the constructor comparator");
        }

        TreeMap<Integer, Integer> natural = new TreeMap<>();
        if (natural.comparator() != null) {
            throw new RuntimeException("natural ordering map should return null comparator");
        }

        TreeMap<Integer, Integer> fromSorted = new TreeMap<>(withCmp);
        if (fromSorted.comparator() != rev) {
            throw new RuntimeException("comparator not preserved when copying from SortedMap");
        }

        HashMap<Integer, Integer> hm = new HashMap<>();
        hm.put(1, 1);
        TreeMap<Integer, Integer> fromMap = new TreeMap<>(hm);
        if (fromMap.comparator() != null) {
            throw new RuntimeException("copying from a plain Map should yield null comparator");
        }
    }
}
