/*
 * @test
 * @summary Verifies that Field.getChar on a non-final static byte field throws IllegalArgumentException via UnsafeStaticByteFieldAccessorImpl, while getByte still returns the stored value.
 * @run main UnsafeStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticByteFieldAccessorImplWitupTest {
    public static byte b = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticByteFieldAccessorImplWitupTest.class.getDeclaredField("b");

        if (f.getByte(null) != 42) {
            throw new AssertionError("getByte returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getChar(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getChar did not throw IllegalArgumentException");
        }
    }
}
