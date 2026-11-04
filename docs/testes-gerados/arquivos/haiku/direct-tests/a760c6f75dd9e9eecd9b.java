/*
 * @test
 * @summary Verifies Unsafe.getAndAddShortAcquire returns the previous value and stores the sum, including wrap-around and negative deltas
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static class Holder {
        short value;
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();
        long offset = unsafe.objectFieldOffset(Holder.class.getDeclaredField("value"));

        Holder h = new Holder();
        h.value = 5;
        short prev = unsafe.getAndAddShortAcquire(h, offset, (short) 3);
        check(prev == 5, "expected previous value 5, got " + prev);
        check(unsafe.getShortAcquire(h, offset) == 8, "expected stored value 8");

        h.value = Short.MAX_VALUE;
        prev = unsafe.getAndAddShortAcquire(h, offset, (short) 1);
        check(prev == Short.MAX_VALUE, "expected previous value MAX_VALUE, got " + prev);
        check(unsafe.getShortAcquire(h, offset) == Short.MIN_VALUE, "expected wrap-around to MIN_VALUE");

        h.value = 0;
        prev = unsafe.getAndAddShortAcquire(h, offset, (short) -2);
        check(prev == 0, "expected previous value 0, got " + prev);
        check(unsafe.getShortAcquire(h, offset) == -2, "expected stored value -2");

        h.value = 7;
        prev = unsafe.getAndAddShortAcquire(h, offset, (short) 0);
        check(prev == 7, "expected previous value 7 for zero delta");
        check(unsafe.getShortAcquire(h, offset) == 7, "expected unchanged value 7 for zero delta");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
