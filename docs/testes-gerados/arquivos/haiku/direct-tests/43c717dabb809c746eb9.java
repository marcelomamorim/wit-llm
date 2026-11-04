/*
 * @test
 * @summary Verifies Field.getInt on a char field, which dispatches to UnsafeCharacterFieldAccessorImpl.getInt
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {
    public char c;

    public static void main(String[] args) throws Exception {
        UnsafeCharacterFieldAccessorImplWitupTest t = new UnsafeCharacterFieldAccessorImplWitupTest();
        Field f = UnsafeCharacterFieldAccessorImplWitupTest.class.getDeclaredField("c");

        t.c = 'A';
        if (f.getInt(t) != 65) {
            throw new RuntimeException("getInt('A') expected 65");
        }

        t.c = (char) 0xFFFF;
        if (f.getInt(t) != 65535) {
            throw new RuntimeException("getInt(0xFFFF) expected 65535 (no sign extension)");
        }

        t.c = (char) 0;
        if (f.getInt(t) != 0) {
            throw new RuntimeException("getInt(0) expected 0");
        }

        t.c = 'Z';
        if (f.getInt(t) != f.getChar(t)) {
            throw new RuntimeException("getInt must be consistent with getChar");
        }

        try {
            f.getInt(null);
            throw new RuntimeException("getInt(null) expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            f.getInt("not a container");
            throw new RuntimeException("getInt(wrong type) expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
