/*
 * @test
 * @summary Checks public Field.getLong on a byte field, including the wrong-receiver IllegalArgumentException path
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static class Holder {
        public byte b = 7;
    }

    public static class Other {
        public int x = 1;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("b");

        long value = f.getLong(new Holder());
        if (value != 7L) {
            throw new RuntimeException("Expected 7 but got " + value);
        }

        boolean thrown = false;
        try {
            f.getLong(new Other());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for wrong receiver type");
        }

        thrown = false;
        try {
            f.getLong("not a holder");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for String receiver");
        }
    }
}
