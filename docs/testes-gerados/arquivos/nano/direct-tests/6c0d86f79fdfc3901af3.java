/* @test    @summary Test for Unsafe.objectFieldOffset method handling null argument    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testObjectFieldOffsetWithNull();
    }

    public static void testObjectFieldOffsetWithNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.objectFieldOffset(null);
            System.out.println("Test failed: NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
