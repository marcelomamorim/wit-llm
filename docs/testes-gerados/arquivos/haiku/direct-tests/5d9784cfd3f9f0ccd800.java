/*
 * @test
 * @summary Verifies that Field.getLong on a volatile char field (qualified char accessor) returns the zero-extended char value and rejects objects of the wrong type
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile char c;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getField("c");

        h.c = 'A';
        check(f.getLong(h) == 65L, "getLong('A') expected 65");

        h.c = Character.MAX_VALUE;
        check(f.getLong(h) == 65535L, "getLong(MAX_VALUE) expected 65535 without sign extension");

        h.c = 0;
        check(f.getLong(h) == 0L, "getLong(0) expected 0");

        f.setChar(h, 'Z');
        check(f.getLong(h) == 90L, "getLong after setChar('Z') expected 90");

        boolean thrown = false;
        try {
            f.getLong("not a Holder");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getLong with wrong object type expected IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
