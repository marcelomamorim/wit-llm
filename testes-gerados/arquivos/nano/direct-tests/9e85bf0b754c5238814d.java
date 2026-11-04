/* @test    @summary Test for Unsafe.staticFieldBase method handling null input and valid Field input    @run main UnsafeWitupTest */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        testStaticFieldBaseWithNull();
        testStaticFieldBaseWithValidField();
    }

    private static void testStaticFieldBaseWithNull() {
        try {
            Unsafe.staticFieldBase(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testStaticFieldBaseWithValidField() {
        try {
            Field field = String.class.getDeclaredField("value");
            Object result = Unsafe.staticFieldBase(field);
            if (result != null) {
                System.out.println("PASS: staticFieldBase returned non-null for String.value");
            } else {
                System.out.println("FAIL: staticFieldBase returned null for String.value");
            }
        } catch (NoSuchFieldException e) {
            System.out.println("FAIL: Field not found: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
