/*
 * @test
 * @summary Verifies that Field.getShort on a static final float field throws IllegalArgumentException, while Field.get still returns the float value
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static final float F = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getField("F");

        Object value = f.get(null);
        if (!(value instanceof Float) || ((Float) value).floatValue() != 1.5f) {
            throw new RuntimeException("Unexpected get() result: " + value);
        }

        try {
            short s = f.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException, got short " + s);
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
