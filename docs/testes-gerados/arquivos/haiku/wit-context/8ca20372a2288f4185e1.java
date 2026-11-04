/*
 * @test
 * @summary Checks public observable behavior of Unsafe.getAndAddCharAcquire on aligned char[] elements
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        check(unsafe != null, "getUnsafe returned null");

        char[] arr = {1, 2, 3};
        long off = Unsafe.ARRAY_CHAR_BASE_OFFSET + 1L * Unsafe.ARRAY_CHAR_INDEX_SCALE;
        char previous = unsafe.getAndAddCharAcquire(arr, off, (char) 5);
        check(previous == 2, "expected previous value 2 but was " + (int) previous);
        check(arr[1] == 7, "expected arr[1] == 7 but was " + (int) arr[1]);
        check(arr[0] == 1 && arr[2] == 3, "neighbouring elements were modified");

        char[] wrap = {(char) 0xFFFF};
        long wrapOff = Unsafe.ARRAY_CHAR_BASE_OFFSET;
        char wrapPrev = unsafe.getAndAddCharAcquire(wrap, wrapOff, (char) 1);
        check(wrapPrev == (char) 0xFFFF, "expected previous value 0xFFFF but was " + (int) wrapPrev);
        check(wrap[0] == (char) 0, "expected wrap-around to 0 but was " + (int) wrap[0]);

        char[] zero = {42};
        char zeroPrev = unsafe.getAndAddCharAcquire(zero, Unsafe.ARRAY_CHAR_BASE_OFFSET, (char) 0);
        check(zeroPrev == 42, "delta 0 should return previous value 42 but was " + (int) zeroPrev);
        check(zero[0] == 42, "delta 0 should not change value");
    }
}
