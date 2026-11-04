/*
 * @test
 * @summary Test jdk.internal.misc.Unsafe.getLoadAverage method for valid and invalid inputs
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test valid input: nelems within bounds and loadavg length sufficient
        double[] loadavg = new double[3];
        int nelems = 3;
        int result = unsafe.getLoadAverage(loadavg, nelems);
        // The native method getLoadAverage0 is platform dependent, but result should be >= 0
        if (result < 0) {
            throw new RuntimeException("getLoadAverage returned negative value");
        }

        // Test valid input: nelems less than loadavg length
        double[] loadavg2 = new double[5];
        int nelems2 = 2;
        int result2 = unsafe.getLoadAverage(loadavg2, nelems2);
        if (result2 < 0) {
            throw new RuntimeException("getLoadAverage returned negative value");
        }

        // Test invalid input: nelems < 0
        try {
            unsafe.getLoadAverage(new double[1], -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for nelems < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // Test invalid input: nelems > 3
        try {
            unsafe.getLoadAverage(new double[4], 4);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for nelems > 3");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // Test invalid input: nelems > loadavg.length
        try {
            unsafe.getLoadAverage(new double[2], 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for nelems > loadavg.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }
}
