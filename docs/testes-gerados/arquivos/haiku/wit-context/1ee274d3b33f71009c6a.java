/*
 * @test
 * @summary Verifies that Field.getShort on a static int field throws IllegalArgumentException and that getInt still returns the stored value
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {

    public static int intField = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("intField");

        boolean thrown = false;
        try {
            f.getShort(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getShort on int field");
        }

        int value = f.getInt(null);
        if (value != 42) {
            throw new RuntimeException("Expected getInt to return 42 but was " + value);
        }
    }
}
