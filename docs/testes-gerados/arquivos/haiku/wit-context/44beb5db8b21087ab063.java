/*
 * @test
 * @summary Witup-derived range-check tests for Arrays.fill(char[], int, int, char)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFullRange();
        testSubRange();
        testEmptyRange();
        testToIndexEqualsLength();
        testFromGreaterThanTo();
        testToIndexBeyondLength();
        testNegativeFromIndex();
        testNullArray();
    }

    static void testFullRange() {
        char[] a = new char[5];
        Arrays.fill(a, 0, a.length, 'x');
        for (char c : a) {
            check(c == 'x', "full range fill failed");
        }
    }

    static void testSubRange() {
        char[] a = {'a', 'b', 'c', 'd', 'e'};
        Arrays.fill(a, 1, 4, 'z');
        check(a[0] == 'a', "index 0 modified");
        check(a[1] == 'z' && a[2] == 'z' && a[3] == 'z', "sub range not filled");
        check(a[4] == 'e', "index 4 modified");
    }

    static void testEmptyRange() {
        char[] a = {'a', 'b', 'c'};
        Arrays.fill(a, 1, 1, 'z');
        check(a[0] == 'a' && a[1] == 'b' && a[2] == 'c', "empty range modified array");
    }

    static void testToIndexEqualsLength() {
        char[] a = new char[3];
        Arrays.fill(a, 2, 3, 'q');
        check(a[2] == 'q', "last element not filled when toIndex equals length");
    }

    static void testFromGreaterThanTo() {
        char[] a = new char[3];
        expectThrows(IllegalArgumentException.class,
                () -> Arrays.fill(a, 2, 1, 'x'),
                "fromIndex greater than toIndex must throw IllegalArgumentException");
    }

    static void testToIndexBeyondLength() {
        char[] a = new char[3];
        expectThrows(ArrayIndexOutOfBoundsException.class,
                () -> Arrays.fill(a, 0, 4, 'x'),
                "toIndex beyond length must throw ArrayIndexOutOfBoundsException");
    }

    static void testNegativeFromIndex() {
        char[] a = new char[3];
        expectThrows(ArrayIndexOutOfBoundsException.class,
                () -> Arrays.fill(a, -1, 2, 'x'),
                "negative fromIndex must throw ArrayIndexOutOfBoundsException");
    }

    static void testNullArray() {
        expectThrows(NullPointerException.class,
                () -> Arrays.fill((char[]) null, 0, 0, 'x'),
                "null array must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectThrows(Class<? extends Throwable> type, Runnable r, String message) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException(message + " but got " + t, t);
        }
        throw new RuntimeException(message + " but no exception was thrown");
    }
}
