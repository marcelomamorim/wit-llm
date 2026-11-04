/*
 * @test
 * @summary Test java.util.Arrays.equals(T[] a, T[] a2, Comparator<? super T> cmp) method
 * @run main java.util.ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullComparator();
        testSameArrayReference();
        testNullArrays();
        testDifferentLengths();
        testEqualArrays();
        testUnequalArrays();
    }

    private static void testNullComparator() {
        String[] a = {"a"};
        String[] b = {"a"};
        try {
            Arrays.equals(a, b, null);
            throw new RuntimeException("Expected NullPointerException for null comparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSameArrayReference() {
        String[] a = {"x", "y"};
        if (!Arrays.equals(a, a, Comparator.naturalOrder())) {
            throw new RuntimeException("Expected true when comparing same array reference");
        }
    }

    private static void testNullArrays() {
        String[] a = null;
        String[] b = {"x"};
        if (Arrays.equals(a, b, Comparator.naturalOrder())) {
            throw new RuntimeException("Expected false when first array is null and second is not");
        }
        if (Arrays.equals(b, null, Comparator.naturalOrder())) {
            throw new RuntimeException("Expected false when second array is null and first is not");
        }
        if (!Arrays.equals((String[])null, (String[])null, Comparator.naturalOrder())) {
            throw new RuntimeException("Expected true when both arrays are null");
        }
    }

    private static void testDifferentLengths() {
        String[] a = {"a", "b"};
        String[] b = {"a"};
        if (Arrays.equals(a, b, Comparator.naturalOrder())) {
            throw new RuntimeException("Expected false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        String[] a = {"a", "b", "c"};
        String[] b = {"a", "b", "c"};
        if (!Arrays.equals(a, b, Comparator.naturalOrder())) {
            throw new RuntimeException("Expected true for equal arrays with natural order comparator");
        }
        // Test with custom comparator ignoring case
        Comparator<String> cmpIgnoreCase = new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        };
        String[] c = {"A", "B", "C"};
        if (!Arrays.equals(a, c, cmpIgnoreCase)) {
            throw new RuntimeException("Expected true for equal arrays ignoring case");
        }
    }

    private static void testUnequalArrays() {
        String[] a = {"a", "b", "c"};
        String[] b = {"a", "x", "c"};
        if (Arrays.equals(a, b, Comparator.naturalOrder())) {
            throw new RuntimeException("Expected false for arrays differing at one element");
        }
    }
}
