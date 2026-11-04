/*
 * @test
 * @summary Verifies byte field reads through Field.getByte and widening getters, which reach UnsafeByteFieldAccessorImpl.getByte
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class Holder {
        private byte b = 42;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("b");
        f.setAccessible(true);
        Holder h = new Holder();

        check(f.getByte(h) == (byte) 42, "getByte should return initial value 42");

        h.b = (byte) -5;
        check(f.getByte(h) == (byte) -5, "getByte should reflect updated negative value");

        h.b = Byte.MAX_VALUE;
        check(f.getByte(h) == Byte.MAX_VALUE, "getByte should return Byte.MAX_VALUE");

        check(f.getInt(h) == Byte.MAX_VALUE, "getInt should widen the byte value");
        check(f.getLong(h) == Byte.MAX_VALUE, "getLong should widen the byte value");

        h.b = (byte) -1;
        check(f.getShort(h) == (short) -1, "getShort should sign-extend the byte value");

        f.setByte(h, (byte) 100);
        check(f.getByte(h) == (byte) 100, "getByte should observe value written by setByte");

        boolean thrown = false;
        try {
            f.getByte(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "getByte on an object of unrelated type should throw IllegalArgumentException");
    }
}
