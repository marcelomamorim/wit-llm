/*
 * @test
 * @summary Verify that getLong on a static boolean field throws IllegalArgumentException through the public Field API
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getField("flag");

        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean expected true");
        }

        try {
            f.getLong(null);
            throw new RuntimeException("expected IllegalArgumentException from getLong(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getLong(new Object());
            throw new RuntimeException("expected IllegalArgumentException from getLong(Object)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
