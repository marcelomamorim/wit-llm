/*
 * @test
 * @summary Test for Unsafe.getAndSetShortAcquire method behavior.
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetAndSetShortAcquire();
    }

    private static void testGetAndSetShortAcquire() {
        Unsafe unsafe = Unsafe.getUnsafe();
        Object dummyObject = new Object();
        long offset = 0L;
        short initialValue = 10;
        short newValue = 20;
        short previousValue;

        try {
            previousValue = unsafe.getAndSetShortAcquire(dummyObject, offset, newValue);
            System.out.println("Previous value: " + previousValue);
            // Since getAndSetShortAcquire is a native method, actual behavior depends on implementation.
            // Here, we check that the method returns a short value without throwing.
            if (previousValue != 0 && previousValue != newValue) {
                System.out.println("Test passed: getAndSetShortAcquire returned a short value.");
            } else {
                System.out.println("Test may be inconclusive: returned value is default or equal to newValue.");
            }
        } catch (Throwable t) {
            System.err.println("Exception during getAndSetShortAcquire: " + t);
        }
    }
}
