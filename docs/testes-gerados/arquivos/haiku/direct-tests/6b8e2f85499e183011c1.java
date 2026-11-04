/*
 * @test
 * @summary Field.getChar on a static boolean field must throw IllegalArgumentException (observable through the public reflection API that dispatches to UnsafeStaticBooleanFieldAccessorImpl.getChar)
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static boolean flag = true;
    public static final boolean CONST = true;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("flag");
        try {
            f.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException from getChar on boolean field");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        Field c = UnsafeStaticBooleanFieldAccessorImplWitupTest.class.getDeclaredField("CONST");
        try {
            c.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException from getChar on final boolean field");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        if (!f.getBoolean(null)) {
            throw new RuntimeException("getBoolean should still return the static value");
        }
    }
}
