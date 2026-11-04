/*
 * @test
 * @summary Field.getByte on a static char field must throw IllegalArgumentException, observable through the public Field API backed by UnsafeStaticCharacterFieldAccessorImpl
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {

    public static char charField = 'x';

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticCharacterFieldAccessorImplWitupTest.class.getField("charField");

        if (f.getChar(null) != 'x') {
            throw new AssertionError("getChar sanity check failed");
        }

        expectIllegalArgument(f, null);
        expectIllegalArgument(f, new UnsafeStaticCharacterFieldAccessorImplWitupTest());

        if (f.getChar(null) != 'x') {
            throw new AssertionError("field value changed unexpectedly");
        }
    }

    private static void expectIllegalArgument(Field f, Object obj) throws IllegalAccessException {
        try {
            byte b = f.getByte(obj);
            throw new AssertionError("expected IllegalArgumentException but got byte " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
