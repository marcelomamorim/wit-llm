/*
 * @test
 * @summary Witup-derived behavioral tests for Arrays.parallelSetAll(long[], IntToLongFunction)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
        testFillsAllElements();
        testEmptyArrayDoesNotInvokeGenerator();
        System.out.println("All tests passed");
    }

    static void testNullGeneratorThrowsNPE() {
        long[] a = new long[3];
        try {
            Arrays.parallelSetAll(a, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null generator");
    }

    static void testNullArrayThrowsNPE() {
        IntToLongFunction g = i -> i;
        try {
            Arrays.parallelSetAll(null, g);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null array");
    }

    static void testFillsAllElements() {
        int n = 100000;
        long[] a = new long[n];
        Arrays.parallelSetAll(a, i -> (long) i * 3L + 1L);
        for (int i = 0; i < n; i++) {
            long expected = (long) i * 3L + 1L;
            if (a[i] != expected) {
                throw new RuntimeException("Wrong value at index " + i + ": " + a[i]);
            }
        }
    }

    static void testEmptyArrayDoesNotInvokeGenerator() {
        AtomicInteger calls = new AtomicInteger();
        long[] a = new long[0];
        Arrays.parallelSetAll(a, i -> {
            calls.incrementAndGet();
            return 0L;
        });
        if (calls.get() != 0) {
            throw new RuntimeException("Generator invoked for empty array: " + calls.get());
        }
    }
}
