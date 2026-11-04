/*
 * @test
 * @summary Tests for java.util.Arrays methods, focusing on exception paths.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithNullComparator();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentElements();
    }

    private static void testEqualsWithNullComparator() {
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"a", "b"};
        try {
            Arrays.equals(arr1, arr2, null);
            System.out.println("FAIL: Expected NullPointerException for null comparator");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null comparator");
        }
    }

    private static void testEqualsWithNullArrays() {
        String[] arr = {"a", "b"};
        try {
            Arrays.equals(null, arr, Comparator.naturalOrder());
            System.out.println("FAIL: Expected NullPointerException for null first array");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null first array");
        }
        try {
            Arrays.equals(arr, null, Comparator.naturalOrder());
            System.out.println("FAIL: Expected NullPointerException for null second array");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null second array");
        }
    }

    private static void testEqualsWithDifferentLengths() {
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"a"};
        boolean result = Arrays.equals(arr1, arr2, Comparator.naturalOrder());
        if (!result) {
            System.out.println("PASS: equals returns false for different length arrays");
        } else {
            System.out.println("FAIL: equals should return false for different length arrays");
        }
    }

    private static void testEqualsWithDifferentElements() {
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"a", "c"};
        boolean result = Arrays.equals(arr1, arr2, Comparator.naturalOrder());
        if (!result) {
            System.out.println("PASS: equals returns false for different elements");
        } else {
            System.out.println("FAIL: equals should return false for different elements");
        }
    }
}
