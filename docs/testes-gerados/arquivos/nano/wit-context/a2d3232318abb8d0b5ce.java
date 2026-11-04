/* @test    @summary Test for Unsafe.arrayBaseOffset(Class<?>) method to verify behavior with null input.    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testArrayBaseOffsetNull();
    }

    public static void testArrayBaseOffsetNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.arrayBaseOffset(null);
            System.out.println("Test failed: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException was correctly thrown.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception type: " + t);
        }
    }
}
