/*
 * @test
 * @summary Tests Unsafe.staticFieldBase null check and behavior for a valid static field
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static int sampleStatic = 42;

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = Unsafe.getUnsafe();

        boolean npeThrown = false;
        try {
            unsafe.staticFieldBase(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null Field");
        }

        Field f = UnsafeWitupTest.class.getField("sampleStatic");
        Object base = unsafe.staticFieldBase(f);
        if (base == null) {
            throw new RuntimeException("Expected non-null base for static field");
        }

        Object baseAgain = unsafe.staticFieldBase(f);
        if (base != baseAgain) {
            throw new RuntimeException("Expected same base for repeated calls on the same static field");
        }
    }
}
