/*
 * @test
 * @summary Checks getAndSetShortAcquire returns the previous short value and stores the new value in a short array
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe u = Unsafe.getUnsafe();
        short[] arr = {5, 7};
        long base = u.arrayBaseOffset(short[].class);
        long off0 = base;
        long off1 = base + 2;

        short prev = u.getAndSetShortAcquire(arr, off0, (short) 9);
        check(prev == 5, "first previous value");
        check(arr[0] == 9, "first stored value");
        check(arr[1] == 7, "neighbour must be untouched");

        prev = u.getAndSetShortAcquire(arr, off1, (short) -3);
        check(prev == 7, "second previous value");
        check(arr[1] == -3, "second stored value");
        check(arr[0] == 9, "first element must be untouched");

        prev = u.getAndSetShortAcquire(arr, off0, Short.MIN_VALUE);
        check(prev == 9, "repeated previous value");
        check(arr[0] == Short.MIN_VALUE, "minimum short stored");

        prev = u.getAndSetShortAcquire(arr, off0, Short.MAX_VALUE);
        check(prev == Short.MIN_VALUE, "boundary previous value");
        check(arr[0] == Short.MAX_VALUE, "maximum short stored");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
