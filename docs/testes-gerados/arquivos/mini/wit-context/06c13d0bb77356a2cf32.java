/*
 * @test
 * @summary Test Unsafe.getLoadAverage method for valid and invalid inputs
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test valid input: nelems within [0..3] and nelems <= loadavg.length
        double[] loadavg = new double[3];
        int nelems = 3;
        int ret = unsafe.getLoadAverage(loadavg, nelems);
        // ret is int, no exception expected
        if (ret < 0) {
            throw new RuntimeException("Unexpected negative return from getLoadAverage");
        }

        // Test nelems < 0 triggers ArrayIndexOutOfBoundsException
        try {
            unsafe.getLoadAverage(new double[1], -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for nelems < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // Test nelems > 3 triggers ArrayIndexOutOfBoundsException
        try {
            unsafe.getLoadAverage(new double[4], 4);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for nelems > 3");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // Test nelems > loadavg.length triggers ArrayIndexOutOfBoundsException
        try {
            unsafe.getLoadAverage(new double[2], 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for nelems > loadavg.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        System.out.println("UnsafeWitupTest passed");
    }
}
