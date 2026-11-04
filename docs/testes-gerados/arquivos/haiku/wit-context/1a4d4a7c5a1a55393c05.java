/*
 * @test
 * @summary Verify that Field.getByte on a static char field throws IllegalArgumentException, which exercises UnsafeStaticCharacterFieldAccessorImpl.getByte
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {
    static char staticChar = 'x';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticCharacterFieldAccessorImplWitupTest.class.getDeclaredField("staticChar");

        try {
            f.getByte(null);
            throw new AssertionError("Expected IllegalArgumentException from getByte on char field");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getByte(new Object());
            throw new AssertionError("Expected IllegalArgumentException from getByte on char field with non-null obj");
        } catch (IllegalArgumentException expected) {
        }

        Object value = f.get(null);
        if (!(value instanceof Character) || ((Character) value).charValue() != 'x') {
            throw new AssertionError("Unexpected value from get: " + value);
        }
    }
}
