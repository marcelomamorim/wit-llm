/* @test    @summary Test for Unsafe.objectFieldOffset(Field f) null argument handling    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testObjectFieldOffsetWithNull();
    }

    private static void testObjectFieldOffsetWithNull() {
        Unsafe unsafe = Unsafe.getUnsafe();
        try {
            unsafe.objectFieldOffset(null);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
