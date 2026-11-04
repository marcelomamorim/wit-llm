/*
 * @test
 * @summary Verifies Unsafe.getAndAddCharAcquire returns the previous char value and stores the wrapped sum in a char[] element
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    private static final Unsafe U = Unsafe.getUnsafe();

    private static long offsetOf(int index) {
        return U.arrayBaseOffset(char[].class) + (long) index * Unsafe.ARRAY_CHAR_INDEX_SCALE;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        char[] a = new char[] {5, 0xFFFF, 10, 0};

        char prev = U.getAndAddCharAcquire(a, offsetOf(0), (char) 3);
        check(prev == 5, "previous value expected 5 but was " + (int) prev);
        check(a[0] == 8, "stored value expected 8 but was " + (int) a[0]);

        prev = U.getAndAddCharAcquire(a, offsetOf(1), (char) 1);
        check(prev == 0xFFFF, "previous value expected 0xFFFF but was " + (int) prev);
        check(a[1] == 0, "wrapped value expected 0 but was " + (int) a[1]);

        prev = U.getAndAddCharAcquire(a, offsetOf(2), (char) -1);
        check(prev == 10, "previous value expected 10 but was " + (int) prev);
        check(a[2] == 9, "negative delta result expected 9 but was " + (int) a[2]);

        prev = U.getAndAddCharAcquire(a, offsetOf(3), (char) 0);
        check(prev == 0, "previous value expected 0 but was " + (int) prev);
        check(a[3] == 0, "zero delta must keep value 0 but was " + (int) a[3]);
    }
}
