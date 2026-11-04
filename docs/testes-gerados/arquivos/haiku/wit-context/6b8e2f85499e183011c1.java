/*
 * @test
 * @summary Verify that Field.getChar on a static boolean field throws IllegalArgumentException, exercising UnsafeStaticBooleanFieldAccessorImpl.getChar through the public reflection API
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static boolean flag = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");

        if (f.getBoolean(null) != true) {
            throw new RuntimeException("getBoolean control check failed");
        }

        try {
            f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException from getChar(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getChar(UnsafeStaticBooleanFieldAccessorImplWitupTest.class);
            throw new RuntimeException("expected IllegalArgumentException from getChar(obj)");
        } catch (IllegalArgumentException expected) {
        }
    }
}
