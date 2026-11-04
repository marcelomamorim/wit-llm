/*
 * @test
 * @summary getLong on a float field via public reflection must throw IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float f = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest o = new UnsafeFloatFieldAccessorImplWitupTest();
        Field field = UnsafeFloatFieldAccessorImplWitupTest.class.getField("f");

        boolean thrown = false;
        try {
            field.getLong(o);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getLong(obj) on float field did not throw IllegalArgumentException");
        }

        thrown = false;
        try {
            field.getLong(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getLong(null) on float field did not throw IllegalArgumentException");
        }

        if (field.getFloat(o) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }
    }
}
