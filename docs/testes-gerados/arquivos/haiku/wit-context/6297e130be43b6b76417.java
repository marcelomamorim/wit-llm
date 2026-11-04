/*
 * @test
 * @summary Verify that reading a float field as long through the public Field API is rejected with IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeFloatFieldAccessorImplWitupTest.class.getDeclaredField("value");
        UnsafeFloatFieldAccessorImplWitupTest target = new UnsafeFloatFieldAccessorImplWitupTest();

        if (field.getFloat(target) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }

        try {
            long v = field.getLong(target);
            throw new RuntimeException("Expected IllegalArgumentException, got " + v);
        } catch (IllegalArgumentException expected) {
        }

        try {
            long v = field.getLong("not a float holder");
            throw new RuntimeException("Expected IllegalArgumentException for wrong receiver, got " + v);
        } catch (IllegalArgumentException expected) {
        }
    }
}
