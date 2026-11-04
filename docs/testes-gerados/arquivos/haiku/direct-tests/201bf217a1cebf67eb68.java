/*
 * @test
 * @summary Verifies Arrays.parallelSetAll(long[], IntToLongFunction) sets each element from the generator and rejects null arguments
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetsAllElements();
        testEmptyArrayIsUnchanged();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
        System.out.println("PASSED");
    }

    static void testSetsAllElements() {
        long[] a = new long[10000];
        Arrays.parallelSetAll(a, i -> (long) i * 3L + 7L);
        for (int i = 0; i < a.length; i++) {
            long expected = (long) i * 3L + 7L;
            if (a[i] != expected) {
                throw new AssertionError("index " + i + ": expected " + expected + " but was " + a[i]);
            }
        }
    }

    static void testEmptyArrayIsUnchanged() {
        long[] a = new long[0];
        IntToLongFunction failIfCalled = i -> {
            throw new AssertionError("generator must not be called for empty array");
        };
        Arrays.parallelSetAll(a, failIfCalled);
        if (a.length != 0) {
            throw new AssertionError("empty array length changed");
        }
    }

    static void testNullGeneratorThrowsNPE() {
        long[] a = new long[4];
        try {
            Arrays.parallelSetAll(a, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null generator");
    }

    static void testNullArrayThrowsNPE() {
        try {
            Arrays.parallelSetAll((long[]) null, i -> (long) i);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null array");
    }
}
