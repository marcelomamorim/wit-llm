/*
 * @test
 * @summary Verify that Field.getBoolean on a static int field reaches the Unsafe static integer accessor and throws IllegalArgumentException
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {

    public static int intValue = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("intValue");

        boolean thrown = false;
        try {
            f.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on static int field");
        }

        if (f.getInt(null) != 42) {
            throw new RuntimeException("getInt regression: expected 42");
        }

        if (((Integer) f.get(null)).intValue() != 42) {
            throw new RuntimeException("get regression: expected Integer 42");
        }
    }
}
