/*
 * @test
 * @summary Verify Field.getShort on a volatile byte field widens the value and rejects an object of an unrelated type
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile byte value;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");
        Holder h = new Holder();

        f.setByte(h, (byte) 42);
        check(f.getShort(h) == (short) 42, "getShort should widen 42");

        f.setByte(h, (byte) -7);
        check(f.getShort(h) == (short) -7, "getShort should preserve sign for -7");

        f.setByte(h, Byte.MIN_VALUE);
        check(f.getShort(h) == (short) Byte.MIN_VALUE, "getShort should widen Byte.MIN_VALUE");

        f.setByte(h, Byte.MAX_VALUE);
        check(f.getShort(h) == (short) Byte.MAX_VALUE, "getShort should widen Byte.MAX_VALUE");

        boolean thrown = false;
        try {
            f.getShort("not a Holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "getShort with an unrelated object should throw IllegalArgumentException");
    }
}
