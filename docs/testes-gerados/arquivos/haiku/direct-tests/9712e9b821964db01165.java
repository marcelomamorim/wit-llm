/*
 * @test
 * @summary Verify that Field.getLong on a static volatile boolean field reaches the qualified static boolean accessor and throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static volatile boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        boolean value = f.getBoolean(null);
        if (!value) {
            throw new RuntimeException("getBoolean returned unexpected value");
        }

        Object boxed = f.get(null);
        if (!Boolean.TRUE.equals(boxed)) {
            throw new RuntimeException("get returned unexpected value: " + boxed);
        }

        boolean thrown = false;
        try {
            f.getLong(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getLong did not throw IllegalArgumentException");
        }
    }
}
