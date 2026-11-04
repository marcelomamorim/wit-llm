/*
 * @test
 * @summary Test java.util.Arrays.equals(T[] a, T[] a2, Comparator<? super T> cmp) method
 * @run main ArraysWitupTest
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
        testElementComparison();
        testEmptyArrays();
    }

    private static void testNullComparator() {
        String[] a = {"a"};
        String[] b = {"a"};
        try {
            Arrays.equals(a, b, null);
            throw new AssertionError("Expected NullPointerException for null comparator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSameArrayReference() {
        String[] a = {"x", "y"};
        boolean result = Arrays.equals(a, a, Comparator.naturalOrder());
        if (!result) {
            throw new AssertionError("Expected true when comparing same array reference");
        }
    }

    private static void testNullArrays() {
        String[] a = null;
        String[] b = {"x"};
        boolean result1 = Arrays.equals(a, b, Comparator.naturalOrder());
        boolean result2 = Arrays.equals(b, null, Comparator.naturalOrder());
        boolean result3 = Arrays.equals(null, null, Comparator.naturalOrder());
        if (result1) {
            throw new AssertionError("Expected false when first array is null and second is not");
        }
        if (result2) {
            throw new AssertionError("Expected false when second array is null and first is not");
        }
        if (!result3) {
            throw new AssertionError("Expected true when both arrays are null");
        }
    }

    private static void testDifferentLengths() {
        String[] a = {"a", "b"};
        String[] b = {"a"};
        boolean result = Arrays.equals(a, b, Comparator.naturalOrder());
        if (result) {
            throw new AssertionError("Expected false when arrays have different lengths");
        }
    }

    private static void testElementComparison() {
        String[] a = {"a", "b", "c"};
        String[] b = {"a", "b", "c"};
        String[] c = {"a", "x", "c"};
        Comparator<String> cmp = Comparator.naturalOrder();
        if (!Arrays.equals(a, b, cmp)) {
            throw new AssertionError("Expected true for equal arrays with natural order comparator");
        }
        if (Arrays.equals(a, c, cmp)) {
            throw new AssertionError("Expected false for arrays differing in one element");
        }
    }

    private static void testEmptyArrays() {
        String[] a = new String[0];
        String[] b = new String[0];
        if (!Arrays.equals(a, b, Comparator.naturalOrder())) {
            throw new AssertionError("Expected true for two empty arrays");
        }
    }
}
