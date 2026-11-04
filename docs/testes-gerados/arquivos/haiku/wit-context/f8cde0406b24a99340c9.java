/*
 * @test
 * @summary Verifies that Field.getByte on a static Object field throws IllegalArgumentException, exercising UnsafeStaticObjectFieldAccessorImpl.getByte through the public reflection API
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object sample = "x";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("sample");

        Object value = f.get(null);
        if (!"x".equals(value)) {
            throw new RuntimeException("Unexpected value from Field.get: " + value);
        }

        try {
            byte b = f.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException, got byte " + b);
        } catch (IllegalArgumentException expected) {
        }

        try {
            byte b = f.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException, got byte " + b);
        } catch (IllegalArgumentException expected) {
        }
    }
}
