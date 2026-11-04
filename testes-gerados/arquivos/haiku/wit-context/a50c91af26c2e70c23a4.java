/*
 * @test
 * @summary Witup-generated checks for float reads of an int field through the public Field API
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedIntegerFieldAccessorImplWitupTest holder = new UnsafeQualifiedIntegerFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getDeclaredField("value");

        float result = field.getFloat(holder);
        if (result != 42.0f) {
            throw new RuntimeException("Expected 42.0f but got " + result);
        }

        holder.value = -7;
        result = field.getFloat(holder);
        if (result != -7.0f) {
            throw new RuntimeException("Expected -7.0f but got " + result);
        }

        boolean thrown = false;
        try {
            field.getFloat("not an instance of the declaring class");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for mismatched receiver");
        }
    }
}
