/*
 * @test
 * @summary Verifies Unsafe.getAndSetShortAcquire returns the previous short value and stores the new value on a short array element
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    private static final Unsafe U = Unsafe.getUnsafe();

    private static long off(short[] a, int i) {
        return U.arrayBaseOffset(short[].class) + (long) i * U.arrayIndexScale(short[].class);
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    private static void testReturnsPreviousAndStores() {
        short[] arr = {1, 2, 3};
        short prev = U.getAndSetShortAcquire(arr, off(arr, 1), (short) 42);
        check(prev == 2, "expected previous value 2, got " + prev);
        check(arr[1] == 42, "expected stored value 42, got " + arr[1]);
    }

    private static void testOnlyTargetElementChanged() {
        short[] arr = {10, 20, 30};
        U.getAndSetShortAcquire(arr, off(arr, 0), (short) -5);
        check(arr[0] == -5, "expected arr[0] == -5, got " + arr[0]);
        check(arr[1] == 20, "arr[1] should be unchanged, got " + arr[1]);
        check(arr[2] == 30, "arr[2] should be unchanged, got " + arr[2]);
    }

    private static void testExtremeValues() {
        short[] arr = {Short.MIN_VALUE, Short.MAX_VALUE};
        short prev1 = U.getAndSetShortAcquire(arr, off(arr, 0), Short.MAX_VALUE);
        check(prev1 == Short.MIN_VALUE, "expected previous MIN_VALUE, got " + prev1);
        check(arr[0] == Short.MAX_VALUE, "expected MAX_VALUE stored, got " + arr[0]);
        short prev2 = U.getAndSetShortAcquire(arr, off(arr, 1), Short.MIN_VALUE);
        check(prev2 == Short.MAX_VALUE, "expected previous MAX_VALUE, got " + prev2);
        check(arr[1] == Short.MIN_VALUE, "expected MIN_VALUE stored, got " + arr[1]);
    }

    public static void main(String[] args) {
        testReturnsPreviousAndStores();
        testOnlyTargetElementChanged();
        testExtremeValues();
    }
}
