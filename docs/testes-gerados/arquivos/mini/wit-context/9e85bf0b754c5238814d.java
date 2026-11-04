/*
 * @test
 * @summary Test Unsafe.staticFieldBase(Field) for null argument and normal usage
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {
    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        // Test that passing null throws NullPointerException
        try {
            unsafe.staticFieldBase(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test with a valid Field instance
        Field field = UnsafeWitupTest.class.getDeclaredField("dummyField");
        Object base = unsafe.staticFieldBase(field);
        if (base == null) {
            throw new Error("staticFieldBase returned null for valid Field");
        }

        System.out.println("Unsafe.staticFieldBase tests passed");
    }

    // dummy field to obtain a Field instance
    private int dummyField;
}
