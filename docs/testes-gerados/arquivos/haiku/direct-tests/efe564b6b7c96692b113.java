/*
 * @test
 * @summary Tests Arrays.parallelSetAll for filling, empty arrays, single invocation per index and null handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsAllElements();
        testEmptyArrayNoGeneratorCalls();
        testEachIndexGeneratedExactlyOnce();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFillsAllElements() {
        int n = 1 << 14;
        String[] array = new String[n];
        Arrays.parallelSetAll(array, i -> "v" + i);
        for (int i = 0; i < n; i++) {
            check(array[i] != null, "element not set at index " + i);
            check(array[i].equals("v" + i), "wrong value at index " + i + ": " + array[i]);
        }
    }

    static void testEmptyArrayNoGeneratorCalls() {
        AtomicIntegerArray calls = new AtomicIntegerArray(1);
        Integer[] array = new Integer[0];
        Arrays.parallelSetAll(array, i -> {
            calls.incrementAndGet(0);
            return i;
        });
        check(array.length == 0, "empty array length changed");
        check(calls.get(0) == 0, "generator called for empty array");
    }

    static void testEachIndexGeneratedExactlyOnce() {
        int n = 1 << 14;
        AtomicIntegerArray counts = new AtomicIntegerArray(n);
        Integer[] array = new Integer[n];
        Arrays.parallelSetAll(array, i -> {
            counts.incrementAndGet(i);
            return i * 2;
        });
        for (int i = 0; i < n; i++) {
            check(counts.get(i) == 1, "index " + i + " generated " + counts.get(i) + " times");
            check(array[i] == i * 2, "wrong value at index " + i);
        }
    }

    static void testNullGeneratorThrowsNPE() {
        String[] array = new String[4];
        try {
            Arrays.parallelSetAll(array, null);
            throw new RuntimeException("expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(generator)
        }
    }

    static void testNullArrayThrowsNPE() {
        try {
            Arrays.parallelSetAll((String[]) null, i -> "x");
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
            // expected: array.length dereference
        }
    }
}
