/*
 * @test
 * @summary Tests Unsafe.ensureClassInitialized null check and class initialization
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static boolean probeInitialized = false;

    static class Probe {
        static {
            UnsafeWitupTest.probeInitialized = true;
        }
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean npeThrown = false;
        try {
            unsafe.ensureClassInitialized(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null class");
        }

        if (probeInitialized) {
            throw new RuntimeException("Probe initialized before ensureClassInitialized");
        }
        unsafe.ensureClassInitialized(Probe.class);
        if (!probeInitialized) {
            throw new RuntimeException("Probe was not initialized by ensureClassInitialized");
        }

        unsafe.ensureClassInitialized(String.class);
        unsafe.ensureClassInitialized(Object.class);
    }
}
