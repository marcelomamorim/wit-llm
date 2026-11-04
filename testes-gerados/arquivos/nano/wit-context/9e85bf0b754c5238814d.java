/* @test    @summary Test for Unsafe.staticFieldBase with null argument    @run main UnsafeWitupTest */

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testStaticFieldBaseNull();
        testStaticFieldBaseValid();
    }

    private static void testStaticFieldBaseNull() {
        try {
            Unsafe unsafe = Unsafe.getUnsafe();
            unsafe.staticFieldBase(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testStaticFieldBaseValid() {
        try {
            Field field = String.class.getDeclaredField("value");
            Unsafe unsafe = Unsafe.getUnsafe();
            Object result = unsafe.staticFieldBase(field);
            if (result != null) {
                System.out.println("PASS: staticFieldBase returned non-null for valid field");
            } else {
                System.out.println("FAIL: staticFieldBase returned null for valid field");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
