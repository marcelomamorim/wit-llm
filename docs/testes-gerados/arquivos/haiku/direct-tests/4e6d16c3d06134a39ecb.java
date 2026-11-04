/*
 * @test
 * @summary Behavioral checks for Arrays.binarySearch(char[], char) on sorted, duplicate, boundary, empty and null inputs
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        char[] a = {'a', 'c', 'e', 'g'};

        check(Arrays.binarySearch(a, 'a') == 0, "first element");
        check(Arrays.binarySearch(a, 'e') == 2, "middle element");
        check(Arrays.binarySearch(a, 'g') == 3, "last element");

        check(Arrays.binarySearch(a, 'b') == -2, "insertion point 1");
        check(Arrays.binarySearch(a, 'A') == -1, "key below range");
        check(Arrays.binarySearch(a, 'z') == -5, "key above range");

        check(Arrays.binarySearch(new char[0], 'a') == -1, "empty array");

        char[] dup = {'x', 'x', 'x'};
        int idx = Arrays.binarySearch(dup, 'x');
        check(idx >= 0 && idx < dup.length && dup[idx] == 'x', "duplicate key");

        char[] single = {'m'};
        check(Arrays.binarySearch(single, 'm') == 0, "single match");
        check(Arrays.binarySearch(single, 'a') == -1, "single below");
        check(Arrays.binarySearch(single, 'z') == -2, "single above");

        char[] bounds = {(char) 0, (char) 0xFFFF};
        check(Arrays.binarySearch(bounds, (char) 0) == 0, "min char");
        check(Arrays.binarySearch(bounds, (char) 0xFFFF) == 1, "max char");

        boolean npe = false;
        try {
            Arrays.binarySearch((char[]) null, 'a');
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null array must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
