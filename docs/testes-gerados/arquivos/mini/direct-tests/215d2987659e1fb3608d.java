/*
 * @test
 * @summary Test java.util.Arrays.setAll method with various inputs
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSetAllBasic();
        testSetAllEmptyArray();
        testSetAllNullGenerator();
    }

    private static void testSetAllBasic() {
        Integer[] arr = new Integer[5];
        Arrays.setAll(arr, new IntFunction<Integer>() {
            public Integer apply(int i) {
                return i * i;
            }
        });
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].equals(i * i)) {
                throw new AssertionError("Element at index " + i + " expected " + (i * i) + " but was " + arr[i]);
            }
        }
    }

    private static void testSetAllEmptyArray() {
        String[] arr = new String[0];
        Arrays.setAll(arr, new IntFunction<String>() {
            public String apply(int i) {
                return "fail";
            }
        });
        if (arr.length != 0) {
            throw new AssertionError("Empty array length changed");
        }
    }

    private static void testSetAllNullGenerator() {
        Integer[] arr = new Integer[3];
        try {
            Arrays.setAll(arr, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
