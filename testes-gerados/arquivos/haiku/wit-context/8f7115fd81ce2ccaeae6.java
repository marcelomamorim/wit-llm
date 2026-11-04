/*
 * @test
 * @summary Verify Field.getInt on a volatile char field, which uses the qualified character accessor, and its IllegalArgumentException for a wrong receiver type
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("c");

        int v = f.getInt(h);
        if (v != 'A') {
            throw new RuntimeException("expected 65 but got " + v);
        }

        f.setChar(h, 'z');
        if (f.getInt(h) != 'z') {
            throw new RuntimeException("getInt did not reflect setChar value");
        }

        try {
            f.getInt("wrong receiver");
            throw new RuntimeException("expected IllegalArgumentException for wrong receiver type");
        } catch (IllegalArgumentException expected) {
        }
    }
}
