/* @test    @summary Test for Unsafe.arrayIndexScale with null argument to verify NullPointerException is thrown    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testArrayIndexScaleNull();
    }

    private static void testArrayIndexScaleNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.arrayIndexScale(null);
            System.out.println("Test failed: NullPointerException was not thrown");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException was thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
