/*
 * @test
 * @summary Observable behavior of Arrays.setAll(T[], IntFunction)
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsArrayInIndexOrder();
        testEmptyArrayDoesNotCallGenerator();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
        testNullResultsAreStored();
        testGeneratorExceptionPropagatesAndStopsLoop();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFillsArrayInIndexOrder() {
        String[] arr = new String[4];
        AtomicInteger calls = new AtomicInteger();
        Arrays.setAll(arr, i -> {
            calls.incrementAndGet();
            return "v" + i;
        });
        check(calls.get() == 4, "generator should be called once per element");
        check("v0".equals(arr[0]), "arr[0] wrong: " + arr[0]);
        check("v1".equals(arr[1]), "arr[1] wrong: " + arr[1]);
        check("v2".equals(arr[2]), "arr[2] wrong: " + arr[2]);
        check("v3".equals(arr[3]), "arr[3] wrong: " + arr[3]);
    }

    static void testEmptyArrayDoesNotCallGenerator() {
        String[] arr = new String[0];
        AtomicInteger calls = new AtomicInteger();
        Arrays.setAll(arr, i -> {
            calls.incrementAndGet();
            return "x";
        });
        check(calls.get() == 0, "generator must not be called for empty array");
        check(arr.length == 0, "length must remain 0");
    }

    static void testNullGeneratorThrowsNPE() {
        String[] arr = new String[2];
        boolean thrown = false;
        try {
            Arrays.setAll(arr, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null generator should throw NullPointerException");
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.setAll((String[]) null, i -> "x");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array should throw NullPointerException");
    }

    static void testNullResultsAreStored() {
        Integer[] arr = new Integer[3];
        Arrays.setAll(arr, i -> (i % 2 == 0) ? null : Integer.valueOf(i));
        check(arr[0] == null, "arr[0] should be null");
        check(Integer.valueOf(1).equals(arr[1]), "arr[1] should be 1");
        check(arr[2] == null, "arr[2] should be null");
    }

    static void testGeneratorExceptionPropagatesAndStopsLoop() {
        String[] arr = new String[5];
        Arrays.fill(arr, "init");
        boolean thrown = false;
        try {
            Arrays.setAll(arr, i -> {
                if (i == 2) {
                    throw new IllegalStateException("boom");
                }
                return "v" + i;
            });
        } catch (IllegalStateException e) {
            thrown = "boom".equals(e.getMessage());
        }
        check(thrown, "generator exception should propagate");
        check("v0".equals(arr[0]), "arr[0] should be set before failure");
        check("v1".equals(arr[1]), "arr[1] should be set before failure");
        check("init".equals(arr[2]), "arr[2] should be unchanged after failure");
        check("init".equals(arr[3]), "arr[3] should be unchanged after failure");
        check("init".equals(arr[4]), "arr[4] should be unchanged after failure");
    }
}
