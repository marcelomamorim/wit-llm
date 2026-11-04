/*
 * @test
 * @summary Basic observable behavior of Unsafe.allocateMemory: zero-size returns 0, positive sizes return non-zero distinct addresses
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        long zero = unsafe.allocateMemory(0);
        if (zero != 0) {
            throw new RuntimeException("allocateMemory(0) expected 0 but was " + zero);
        }

        long p1 = unsafe.allocateMemory(16);
        if (p1 == 0) {
            throw new RuntimeException("allocateMemory(16) returned 0");
        }

        long p2 = unsafe.allocateMemory(16);
        if (p2 == 0) {
            throw new RuntimeException("second allocateMemory(16) returned 0");
        }

        if (p1 == p2) {
            throw new RuntimeException("two live allocations returned the same address: " + p1);
        }
    }
}
