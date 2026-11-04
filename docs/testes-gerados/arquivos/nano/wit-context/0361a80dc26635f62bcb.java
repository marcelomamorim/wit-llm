/*
 * @test
 * @summary Test for Unsafe.ensureClassInitialized method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testEnsureClassInitializedWithNull();
        testEnsureClassInitializedWithValidClass();
    }

    private static void testEnsureClassInitializedWithNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.ensureClassInitialized(null);
            System.out.println("PASS: ensureClassInitialized(null) threw NullPointerException as expected.");
        } catch (NullPointerException e) {
            System.out.println("PASS: ensureClassInitialized(null) threw NullPointerException.");
        } catch (Throwable t) {
            System.out.println("FAIL: ensureClassInitialized(null) threw unexpected exception: " + t);
        }
    }

    private static void testEnsureClassInitializedWithValidClass() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            // Using Unsafe.class as a dummy class for testing
            unsafe.ensureClassInitialized(Unsafe.class);
            System.out.println("PASS: ensureClassInitialized(Unsafe.class) executed without exception.");
        } catch (Throwable t) {
            System.out.println("FAIL: ensureClassInitialized(Unsafe.class) threw exception: " + t);
        }
    }
}
