/*
 * @test
 * @summary Witup-derived checks for Arrays.setAll(T[], IntFunction): fills elements and rejects null arguments
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        String[] arr = new String[4];
        Arrays.setAll(arr, i -> "v" + i);
        for (int i = 0; i < arr.length; i++) {
            if (!("v" + i).equals(arr[i])) {
                throw new RuntimeException("Unexpected element at " + i + ": " + arr[i]);
            }
        }

        String[] empty = new String[0];
        Arrays.setAll(empty, i -> "never");
        if (empty.length != 0) {
            throw new RuntimeException("Empty array length changed");
        }

        IntFunction<String> nullGenerator = null;
        try {
            Arrays.setAll(new String[2], nullGenerator);
            throw new RuntimeException("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
        }

        try {
            Arrays.setAll((String[]) null, i -> "x");
            throw new RuntimeException("Expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        }
    }
}
