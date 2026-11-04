/*
 * @test
 * @summary Test for jdk.internal.misc.Unsafe.shouldBeInitialized(Class<?>) method
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testShouldBeInitializedWithNull();
        testShouldBeInitializedWithValidClass();
    }

    private static void testShouldBeInitializedWithNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.shouldBeInitialized(null);
            System.out.println("Test failed: NullPointerException was not thrown for null input.");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException thrown as expected for null input.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }

    private static void testShouldBeInitializedWithValidClass() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            boolean result = unsafe.shouldBeInitialized(String.class);
            System.out.println("Result of shouldBeInitialized(String.class): " + result);
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
