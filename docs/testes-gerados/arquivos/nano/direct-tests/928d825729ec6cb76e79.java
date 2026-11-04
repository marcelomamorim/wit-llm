/* @test    @summary Test ArrayList.sort with various comparators to ensure correct sorting behavior and exception handling.    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSortWithNullComparator();
        testSortWithIntegerComparator();
        testSortWithStringComparator();
        testSortModCountChange();
    }

    static void testSortWithNullComparator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("banana");
        list.add("apple");
        list.add("cherry");
        list.sort(null); // Should sort using natural order
        assert list.get(0).equals("apple") : "Expected 'apple' at index 0";
        assert list.get(1).equals("banana") : "Expected 'banana' at index 1";
        assert list.get(2).equals("cherry") : "Expected 'cherry' at index 2";
    }

    static void testSortWithIntegerComparator() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort(Comparator.naturalOrder());
        assert list.get(0) == 1 : "Expected 1 at index 0";
        assert list.get(1) == 2 : "Expected 2 at index 1";
        assert list.get(2) == 3 : "Expected 3 at index 2";
    }

    static void testSortWithStringComparator() {
        ArrayList<String> list = new ArrayList<>();
        list.add("delta");
        list.add("alpha");
        list.add("charlie");
        list.sort(Comparator.comparing(String::length));
        assert list.get(0).equals("alpha") : "Expected 'alpha' at index 0";
        assert list.get(1).equals("delta") : "Expected 'delta' at index 1";
        assert list.get(2).equals("charlie") : "Expected 'charlie' at index 2";
    }

    static void testSortModCountChange() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            list.sort((s1, s2) -> {
                list.add("extra"); // Modifies list during sort
                return s1.compareTo(s2);
            });
            assert false : "Expected ConcurrentModificationException";
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }
}
