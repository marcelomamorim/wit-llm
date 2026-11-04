/*
 * @test
 * @summary Verifies that Field.getShort on a static char field throws IllegalArgumentException, exercising UnsafeStaticCharacterFieldAccessorImpl.getShort through the public reflection API
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {

    public static class Holder {
        public static char c = 'x';
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getField("c");
        boolean thrown = false;
        try {
            field.getShort(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getShort on char field");
        }

        Field intField = Holder.class.getField("c");
        if (Character.valueOf(Holder.c).charValue() != 'x') {
            throw new AssertionError("Unexpected char value");
        }
        if (intField.getChar(null) != 'x') {
            throw new AssertionError("getChar should still return the stored value");
        }
    }
}
