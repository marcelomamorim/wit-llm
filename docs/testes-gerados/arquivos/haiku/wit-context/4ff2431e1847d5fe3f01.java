/*
 * @test
 * @summary Field.getBoolean on a static int field must throw IllegalArgumentException, while get and getInt keep returning the int value
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static int intField = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("intField");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from Field.getBoolean on int field");
        }

        if (f.getInt(null) != 42) {
            throw new RuntimeException("getInt returned unexpected value");
        }

        Object value = f.get(null);
        if (!Integer.valueOf(42).equals(value)) {
            throw new RuntimeException("get returned unexpected value: " + value);
        }
    }
}
