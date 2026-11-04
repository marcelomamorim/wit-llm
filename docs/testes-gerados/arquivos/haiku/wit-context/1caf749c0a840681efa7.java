/*
 * @test
 * @summary Checks Field.getShort on a byte field, which dispatches to UnsafeByteFieldAccessorImpl.getShort, and the IllegalArgumentException for an object of the wrong type
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static class Holder {
        public byte b;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("b");
        Holder h = new Holder();

        h.b = 42;
        check(f.getShort(h) == (short) 42, "positive byte must widen to short");

        h.b = -128;
        check(f.getShort(h) == (short) -128, "Byte.MIN_VALUE must widen to short");

        h.b = 127;
        check(f.getShort(h) == (short) 127, "Byte.MAX_VALUE must widen to short");

        h.b = 0;
        check(f.getShort(h) == (short) 0, "zero must be preserved as short");

        h.b = -1;
        check(f.getShort(h) == (short) -1, "negative byte must sign-extend to short");

        try {
            f.getShort(new Object());
            throw new AssertionError("expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
