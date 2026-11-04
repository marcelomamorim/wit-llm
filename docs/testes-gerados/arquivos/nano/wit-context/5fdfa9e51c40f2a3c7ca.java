/* @test    @summary Test for Unsafe.objectFieldOffset to verify NullPointerException on null arguments    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testObjectFieldOffsetNullArguments();
    }

    private static void testObjectFieldOffsetNullArguments() {
        Unsafe unsafe = Unsafe.getUnsafe();
        boolean exceptionThrown = false;
        try {
            unsafe.objectFieldOffset(null, "fieldName");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when first argument is null");
        }

        exceptionThrown = false;
        try {
            unsafe.objectFieldOffset(SomeClass.class, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when second argument is null");
        }
    }

    private static class SomeClass {
        int dummy;
    }
}
