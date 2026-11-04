/*
 * @test
 * @summary Tests argument validation and result contract of Unsafe.getLoadAverage
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        expectAIOOBE(unsafe, new double[3], -1);
        expectAIOOBE(unsafe, new double[3], 4);
        expectAIOOBE(unsafe, new double[1], 2);
        expectAIOOBE(unsafe, new double[0], 1);

        int r0 = unsafe.getLoadAverage(new double[3], 0);
        if (r0 != 0 && r0 != -1) {
            throw new RuntimeException("Unexpected result for nelems=0: " + r0);
        }

        int r3 = unsafe.getLoadAverage(new double[3], 3);
        if (r3 != 3 && r3 != -1) {
            throw new RuntimeException("Unexpected result for nelems=3: " + r3);
        }

        int r2 = unsafe.getLoadAverage(new double[2], 2);
        if (r2 != 2 && r2 != -1) {
            throw new RuntimeException("Unexpected result for nelems=2: " + r2);
        }
    }

    static void expectAIOOBE(Unsafe unsafe, double[] arr, int nelems) {
        try {
            unsafe.getLoadAverage(arr, nelems);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for nelems=" + nelems
                + ", length=" + arr.length);
    }
}
