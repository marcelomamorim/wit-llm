/*
 * @test
 * @summary Verifies observable Field.getDouble behavior on char fields and the IllegalArgumentException for an incompatible receiver
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static class Holder {
        public char c = 'A';
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("c");
        Holder h = new Holder();

        double first = f.getDouble(h);
        if (first != (double) 'A') {
            throw new RuntimeException("expected 65.0 but got " + first);
        }

        h.c = 'Z';
        double second = f.getDouble(h);
        if (second != (double) 'Z') {
            throw new RuntimeException("expected 90.0 but got " + second);
        }

        boolean thrown = false;
        try {
            f.getDouble("not a Holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for incompatible receiver");
        }
    }
}
