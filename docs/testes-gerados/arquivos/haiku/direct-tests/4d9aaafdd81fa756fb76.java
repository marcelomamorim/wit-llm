/*
 * @test
 * @summary Field.getChar on a static float field must throw IllegalArgumentException, exercising UnsafeStaticFloatFieldAccessorImpl.getChar
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    static float staticFloat = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("staticFloat");

        if (f.getFloat(null) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }

        try {
            char c = f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException, got char " + (int) c);
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            f.getChar(new Object());
            throw new RuntimeException("expected IllegalArgumentException for non-null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
