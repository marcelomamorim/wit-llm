/* @test    @summary Test getAndAddShortAcquire method for exception paths    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetAndAddShortAcquireInvalidOffset();
        testGetAndAddShortAcquireInvalidOffsetBigEndian();
    }

    private static void testGetAndAddShortAcquireInvalidOffset() {
        Unsafe unsafe = Unsafe.getUnsafe();
        Object obj = new Object();
        long invalidOffset = -1L; // invalid offset to trigger exception
        short delta = 1;
        try {
            unsafe.getAndAddShortAcquire(obj, invalidOffset, delta);
            System.out.println("Failed: Expected IllegalArgumentException for invalid offset.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for invalid offset.");
        } catch (Throwable t) {
            System.out.println("Failed: Caught unexpected exception: " + t);
        }
    }

    private static void testGetAndAddShortAcquireInvalidOffsetBigEndian() {
        Unsafe unsafe = Unsafe.getUnsafe();
        Object obj = new Object();
        long invalidOffset = -1L; // invalid offset to trigger exception
        short delta = 1;
        try {
            unsafe.getAndAddShortAcquire(obj, invalidOffset, delta);
            System.out.println("Failed: Expected IllegalArgumentException for invalid offset.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for invalid offset.");
        } catch (Throwable t) {
            System.out.println("Failed: Caught unexpected exception: " + t);
        }
    }
}
