/*
 * @test
 * @summary Checks Field.getLong on a volatile char field, which uses the qualified character accessor, including the IllegalArgumentException guard for incompatible receivers.
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class Holder {
        volatile char c;
    }

    static class Other {
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("c");
        Holder h = new Holder();

        f.setChar(h, 'A');
        long v1 = f.getLong(h);
        if (v1 != 65L) {
            throw new RuntimeException("expected 65 but was " + v1);
        }

        f.setChar(h, '\uFFFF');
        long v2 = f.getLong(h);
        if (v2 != 65535L) {
            throw new RuntimeException("expected 65535 but was " + v2);
        }

        f.setChar(h, '\u0000');
        long v3 = f.getLong(h);
        if (v3 != 0L) {
            throw new RuntimeException("expected 0 but was " + v3);
        }

        boolean thrownForOther = false;
        try {
            f.getLong(new Other());
        } catch (IllegalArgumentException e) {
            thrownForOther = true;
        }
        if (!thrownForOther) {
            throw new RuntimeException("expected IllegalArgumentException for incompatible receiver");
        }

        boolean thrownForNull = false;
        try {
            f.getLong(null);
        } catch (IllegalArgumentException e) {
            thrownForNull = true;
        }
        if (!thrownForNull) {
            throw new RuntimeException("expected IllegalArgumentException for null receiver");
        }
    }
}
