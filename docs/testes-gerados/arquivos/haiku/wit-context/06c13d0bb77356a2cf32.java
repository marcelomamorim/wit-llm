/*
 * @test
 * @summary Witup-derived tests for Unsafe.getLoadAverage argument validation
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        expectAioobe(unsafe, new double[3], -1);
        expectAioobe(unsafe, new double[3], 4);
        expectAioobe(unsafe, new double[10], 4);
        expectAioobe(unsafe, new double[2], 3);
        expectAioobe(unsafe, new double[0], 1);

        int r1 = unsafe.getLoadAverage(new double[3], 3);
        if (r1 < -1 || r1 > 3) {
            fail("unexpected return value for nelems=3: " + r1);
        }

        int r2 = unsafe.getLoadAverage(new double[1], 1);
        if (r2 < -1 || r2 > 1) {
            fail("unexpected return value for nelems=1: " + r2);
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void expectAioobe(Unsafe unsafe, double[] loadavg, int nelems) {
        try {
            unsafe.getLoadAverage(loadavg, nelems);
            fail("expected ArrayIndexOutOfBoundsException for length=" + loadavg.length + " nelems=" + nelems);
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void fail(String msg) {
        failures++;
        System.err.println("FAIL: " + msg);
    }
}
