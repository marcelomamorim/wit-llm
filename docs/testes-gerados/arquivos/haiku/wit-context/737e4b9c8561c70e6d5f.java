/*
 * @test
 * @summary Checks toArray on lists returned by Collections.nCopies, including the negative-count precondition
 * @run main CollectionsWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testZeroCount();
        testFilledArray();
        testNullElementArray();
        testNegativeCountRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroCount() {
        List<Integer> list = Collections.nCopies(0, Integer.valueOf(7));
        Object[] array = list.toArray();
        check(array != null, "toArray returned null for empty copies");
        check(array.length == 0, "expected length 0 but was " + array.length);
    }

    static void testFilledArray() {
        Integer value = Integer.valueOf(7);
        List<Integer> list = Collections.nCopies(4, value);
        Object[] array = list.toArray();
        check(array.length == 4, "expected length 4 but was " + array.length);
        for (int i = 0; i < array.length; i++) {
            check(array[i] == value, "element " + i + " differs from repeated value");
        }
        check(Arrays.equals(array, new Object[] {value, value, value, value}),
              "array content mismatch");
    }

    static void testNullElementArray() {
        List<Object> list = Collections.nCopies(3, null);
        Object[] array = list.toArray();
        check(array.length == 3, "expected length 3 but was " + array.length);
        for (int i = 0; i < array.length; i++) {
            check(array[i] == null, "element " + i + " should be null");
        }
    }

    static void testNegativeCountRejected() {
        boolean thrown = false;
        try {
            Collections.nCopies(-1, Integer.valueOf(1));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "nCopies with negative count must throw IllegalArgumentException");
    }
}
