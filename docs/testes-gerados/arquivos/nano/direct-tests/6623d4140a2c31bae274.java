/* @test    @summary Test shouldBeInitialized method of Unsafe class    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testShouldBeInitializedWithNull();
        testShouldBeInitializedWithClass();
    }

    private static void testShouldBeInitializedWithNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.shouldBeInitialized(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null argument");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testShouldBeInitializedWithClass() {
        Unsafe unsafe = Unsafe.getUnsafe();
        class Dummy {}
        boolean result = unsafe.shouldBeInitialized(Dummy.class);
        System.out.println("shouldBeInitialized(Dummy.class) returned: " + result);
        // No specific expected value; just ensure no exception thrown
    }
}
