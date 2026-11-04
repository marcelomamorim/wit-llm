/*
 * @test
 * @summary Field.getByte on a static int field must throw IllegalArgumentException (observable path to UnsafeStaticIntegerFieldAccessorImpl.getByte)
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static int value = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getField("value");

        if (f.getInt(null) != 42) {
            throw new RuntimeException("getInt returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getByte on static int field did not throw IllegalArgumentException");
        }
    }
}
