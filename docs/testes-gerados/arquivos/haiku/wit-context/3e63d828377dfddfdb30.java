/*
 * @test
 * @summary Witup-generated behavioral checks for Arrays.parallelPrefix(long[], LongBinaryOperator)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    public static void main(String[] args) {
        prefixSumsAreComputed();
        emptyArrayIsUnchanged();
        singleElementIsUnchanged();
        largeArrayPrefixSumsAreComputed();
        nullOperatorThrowsNPE();
        nullArrayThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void prefixSumsAreComputed() {
        long[] a = {1L, 2L, 3L, 4L};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a[0] == 1L && a[1] == 3L && a[2] == 6L && a[3] == 10L,
              "prefix sums are incorrect");
    }

    static void emptyArrayIsUnchanged() {
        long[] a = new long[0];
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a.length == 0, "empty array length changed");
    }

    static void singleElementIsUnchanged() {
        long[] a = {7L};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a[0] == 7L, "single element was modified");
    }

    static void largeArrayPrefixSumsAreComputed() {
        int n = 100000;
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = 1L;
        }
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        for (int i = 0; i < n; i++) {
            check(a[i] == (long) (i + 1), "large array prefix sum mismatch at index " + i);
        }
    }

    static void nullOperatorThrowsNPE() {
        long[] a = {1L, 2L};
        try {
            Arrays.parallelPrefix(a, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null operator");
    }

    static void nullArrayThrowsNPE() {
        try {
            Arrays.parallelPrefix((long[]) null, (x, y) -> x + y);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null array");
    }
}
