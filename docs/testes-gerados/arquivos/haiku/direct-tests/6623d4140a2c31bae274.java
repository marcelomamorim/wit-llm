/*
 * @test
 * @summary Verifies Unsafe.shouldBeInitialized null check and initialization state observability
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    static class Holder {
        static int value = 42;
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean npeThrown = false;
        try {
            unsafe.shouldBeInitialized(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("expected NullPointerException for null class");
        }

        ClassLoader loader = UnsafeWitupTest.class.getClassLoader();
        String holderName = "UnsafeWitupTest$Holder";

        Class<?> holder = Class.forName(holderName, false, loader);
        if (!unsafe.shouldBeInitialized(holder)) {
            throw new AssertionError("expected true for loaded but uninitialized class");
        }

        Class.forName(holderName, true, loader);
        if (unsafe.shouldBeInitialized(holder)) {
            throw new AssertionError("expected false for initialized class");
        }

        if (unsafe.shouldBeInitialized(UnsafeWitupTest.class)) {
            throw new AssertionError("expected false for already initialized main class");
        }
    }
}
