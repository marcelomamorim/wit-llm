/*
 * @test
 * @summary Verify Unsafe.ensureClassInitialized null handling and class initialization
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    static int initCount = 0;

    static class Probe {
        static {
            UnsafeWitupTest.initCount++;
        }
    }

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean npeThrown = false;
        try {
            unsafe.ensureClassInitialized(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null class");
        }
        if (initCount != 0) {
            throw new RuntimeException("Probe initialized unexpectedly: " + initCount);
        }

        unsafe.ensureClassInitialized(Probe.class);
        if (initCount != 1) {
            throw new RuntimeException("Expected Probe to be initialized once, was: " + initCount);
        }

        unsafe.ensureClassInitialized(Probe.class);
        if (initCount != 1) {
            throw new RuntimeException("Repeated initialization must be idempotent, was: " + initCount);
        }
    }
}
