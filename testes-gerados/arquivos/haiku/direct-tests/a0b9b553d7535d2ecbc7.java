/*
 * @test
 * @summary Verifies byte reads, writes and wrong-receiver behavior of a volatile byte field accessed through public reflection
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    public static class Holder {
        public volatile byte b;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("b");
        Holder h = new Holder();

        h.b = 42;
        if (f.getByte(h) != 42) {
            throw new RuntimeException("getByte expected 42");
        }

        h.b = -7;
        if (f.getByte(h) != -7) {
            throw new RuntimeException("getByte expected -7");
        }

        f.setByte(h, (byte) 5);
        if (h.b != 5 || f.getByte(h) != 5) {
            throw new RuntimeException("setByte did not update field");
        }

        Object boxed = f.get(h);
        if (((Byte) boxed).byteValue() != 5) {
            throw new RuntimeException("get returned wrong boxed value");
        }

        boolean rejected = false;
        try {
            f.getByte("not a holder");
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        if (!rejected) {
            throw new RuntimeException("getByte with wrong receiver must throw IllegalArgumentException");
        }
    }
}
