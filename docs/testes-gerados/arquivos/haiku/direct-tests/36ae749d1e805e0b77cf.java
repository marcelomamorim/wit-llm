/*
 * @test
 * @summary Verify that Field.getChar on a float field reaches UnsafeFloatFieldAccessorImpl.getChar and throws IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest t = new UnsafeFloatFieldAccessorImplWitupTest();
        Field f = UnsafeFloatFieldAccessorImplWitupTest.class.getField("value");

        if (f.getFloat(t) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }

        try {
            f.getChar(t);
            throw new RuntimeException("expected IllegalArgumentException for getChar on float field");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException for getChar with null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
