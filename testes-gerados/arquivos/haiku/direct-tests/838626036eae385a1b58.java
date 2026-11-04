/*
 * @test
 * @summary Verifies Field.getLong on a volatile byte field, which is served by the unsafe qualified byte accessor, returns the sign-extended byte value
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class Holder {
        volatile byte vb;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("vb");
        f.setAccessible(true);
        Holder h = new Holder();

        f.setByte(h, (byte) -5);
        check(f.getLong(h) == -5L, "negative byte should widen to -5L");
        check(f.getLong(h) == (long) f.getByte(h), "getLong should match widened getByte");

        f.setByte(h, Byte.MAX_VALUE);
        check(f.getLong(h) == 127L, "Byte.MAX_VALUE should widen to 127L");

        f.setByte(h, Byte.MIN_VALUE);
        check(f.getLong(h) == -128L, "Byte.MIN_VALUE should widen to -128L");

        f.setByte(h, (byte) 0);
        check(f.getLong(h) == 0L, "zero byte should yield 0L");

        boolean thrown = false;
        try {
            f.getLong(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getLong with an object of the wrong type should throw IllegalArgumentException");
    }
}
