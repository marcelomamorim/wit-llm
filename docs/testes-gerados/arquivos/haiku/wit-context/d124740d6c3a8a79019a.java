/*
 * @test
 * @summary Verifies that Field.getBoolean on a float instance field reaches UnsafeFloatFieldAccessorImpl.getBoolean and throws IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public float instanceValue = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest target = new UnsafeFloatFieldAccessorImplWitupTest();
        Field field = UnsafeFloatFieldAccessorImplWitupTest.class.getField("instanceValue");

        float before = field.getFloat(target);
        if (before != 1.5f) {
            throw new AssertionError("Unexpected float value: " + before);
        }

        boolean thrown = false;
        try {
            field.getBoolean(target);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getBoolean on float field");
        }

        if (field.getFloat(target) != 1.5f) {
            throw new AssertionError("Float field value changed after rejected getBoolean");
        }
    }
}
