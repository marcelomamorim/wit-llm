/* @test    @summary Test getLoadAverage method for valid and invalid inputs    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testGetLoadAverageValid();
        testGetLoadAverageNegativeNelems();
        testGetLoadAverageExceedingLoadavgLength();
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

    private static void testGetLoadAverageNegativeNelems() {
        Unsafe unsafe = Unsafe.getUnsafe();
        double[] loadavg = new double[3];
        int nelems = -1;
        try {
            unsafe.getLoadAverage(loadavg, nelems);
            System.err.println("Negative nelems test failed: no exception thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Negative nelems test passed, exception caught.");
        } catch (Throwable t) {
            System.err.println("Negative nelems test failed, wrong exception: " + t);
        }
    }

    private static void testGetLoadAverageExceedingLoadavgLength() {
        Unsafe unsafe = Unsafe.getUnsafe();
        double[] loadavg = new double[2];
        int nelems = 3;
        try {
            unsafe.getLoadAverage(loadavg, nelems);
            System.err.println("Exceeding loadavg length test failed: no exception thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exceeding loadavg length test passed, exception caught.");
        } catch (Throwable t) {
            System.err.println("Exceeding loadavg length test failed, wrong exception: " + t);
        }
    }
}
