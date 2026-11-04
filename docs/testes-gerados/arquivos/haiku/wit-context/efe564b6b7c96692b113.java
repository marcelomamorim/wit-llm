/*
 * @test
 * @summary WITUP-derived tests for Arrays.parallelSetAll null handling and fill behavior
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.function.IntFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
        testFillsAllIndices();
        testEmptyArrayUnchanged();
    }

    static void testNullGeneratorThrowsNPE() {
        String[] a = new String[3];
        try {
            Arrays.parallelSetAll(a, null);
            throw new RuntimeException("expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullArrayThrowsNPE() {
        String[] n = null;
        IntFunction<String> g = i -> "x" + i;
        try {
            Arrays.parallelSetAll(n, g);
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        }
    }

    static void testFillsAllIndices() {
        String[] a = new String[1000];
        IntFunction<String> g = i -> "v" + i;
        Arrays.parallelSetAll(a, g);
        for (int i = 0; i < a.length; i++) {
            if (!("v" + i).equals(a[i])) {
                throw new RuntimeException("unexpected value at " + i + ": " + a[i]);
            }
        }
    }

    static void testEmptyArrayUnchanged() {
        Integer[] e = new Integer[0];
        Arrays.parallelSetAll(e, i -> i);
        if (e.length != 0) {
            throw new RuntimeException("empty array length changed");
        }
    }
}
