/* @test    @summary Test getLoadAverage for valid and invalid inputs    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetLoadAverageValid();
        testGetLoadAverageNegative();
        testGetLoadAverageExceeding();
        testGetLoadAverageNullArray();
    }

    private static void testGetLoadAverageValid() {
        Unsafe unsafe = Unsafe.getUnsafe();
        double[] loadavg = new double[3];
        int nelems = 3;
        try {
            int result = unsafe.getLoadAverage(loadavg, nelems);
            System.out.println("Valid input test passed, result: " + result);
        } catch (Throwable t) {
            System.err.println("Valid input test failed: " + t);
        }
    }

    private static void testGetLoadAverageNegative() {
        Unsafe unsafe = Unsafe.getUnsafe();
        double[] loadavg = new double[3];
        int nelems = -1;
        try {
            unsafe.getLoadAverage(loadavg, nelems);
            System.err.println("Negative nelems test failed: exception expected but not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Negative nelems test passed, caught expected exception");
        } catch (Throwable t) {
            System.err.println("Negative nelems test failed, unexpected exception: " + t);
        }
    }

    private static void testGetLoadAverageExceeding() {
        Unsafe unsafe = Unsafe.getUnsafe();
        double[] loadavg = new double[3];
        int nelems = 4;
        try {
            unsafe.getLoadAverage(loadavg, nelems);
            System.err.println("Exceeding nelems test failed: exception expected but not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exceeding nelems test passed, caught expected exception");
        } catch (Throwable t) {
            System.err.println("Exceeding nelems test failed, unexpected exception: " + t);
        }
    }

    private static void testGetLoadAverageNullArray() {
        Unsafe unsafe = Unsafe.getUnsafe();
        double[] loadavg = null;
        int nelems = 2;
        try {
            unsafe.getLoadAverage(loadavg, nelems);
            System.err.println("Null array test failed: exception expected but not thrown");
        } catch (NullPointerException e) {
            System.out.println("Null array test passed, caught expected exception");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Null array test passed, caught expected exception");
        } catch (Throwable t) {
            System.err.println("Null array test failed, unexpected exception: " + t);
        }
    }
}
