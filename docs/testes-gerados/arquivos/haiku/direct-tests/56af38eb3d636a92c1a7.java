/*
 * @test
 * @summary Field.getByte on a char field must throw IllegalArgumentException, as delegated to UnsafeCharacterFieldAccessorImpl.getByte
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    static class Holder {
        char c = 'A';
        static char sc = 'B';
    }

    static void checkGetByteThrowsIAE(Field f, Object obj) throws Exception {
        try {
            byte b = f.getByte(obj);
            throw new RuntimeException("Expected IllegalArgumentException, got value " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field instanceField = Holder.class.getDeclaredField("c");
        checkGetByteThrowsIAE(instanceField, h);

        Field staticField = Holder.class.getDeclaredField("sc");
        checkGetByteThrowsIAE(staticField, null);

        // The char value must remain readable through the supported accessor.
        if (instanceField.getChar(h) != 'A') {
            throw new RuntimeException("getChar returned unexpected value");
        }
    }
}
