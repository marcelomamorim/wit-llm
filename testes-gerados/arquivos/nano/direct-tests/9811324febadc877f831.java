/* @test    @summary Test for Arrays.equals with custom comparator    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithComparator();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithEqualArrays();
    }

    static void testEqualsWithComparator() {
        String[] a = {"apple", "banana"};
        String[] b = {"APPLE", "BANANA"};
        Comparator<String> caseInsensitiveCmp = String.CASE_INSENSITIVE_ORDER;
        boolean result = Arrays.equals(a, b, caseInsensitiveCmp);
        if (result) {
            throw new AssertionError("Expected arrays to be unequal with case-insensitive comparator");
        }
    }

    static void testEqualsWithNullArrays() {
        String[] a = null;
        String[] b = {"test"};
        try {
            Arrays.equals(a, b, String.CASE_INSENSITIVE_ORDER);
            throw new AssertionError("Expected NullPointerException for null array a");
        } catch (NullPointerException e) {
            // expected
        }
        try {
            Arrays.equals(b, a, String.CASE_INSENSITIVE_ORDER);
            throw new AssertionError("Expected NullPointerException for null array b");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEqualsWithDifferentLengths() {
        String[] a = {"one", "two"};
        String[] b = {"one"};
        boolean result = Arrays.equals(a, b, String.CASE_INSENSITIVE_ORDER);
        if (result) {
            throw new AssertionError("Expected arrays of different lengths to be unequal");
        }
    }

    static void testEqualsWithEqualArrays() {
        String[] a = {"hello", "world"};
        String[] b = {"hello", "world"};
        boolean result = Arrays.equals(a, b, String.CASE_INSENSITIVE_ORDER);
        if (!result) {
            throw new AssertionError("Expected identical arrays to be equal");
        }
    }
}
