/*
 * @test
 * @summary Verify Field.getDouble on a non-final byte field, which is served by UnsafeByteFieldAccessorImpl.getDouble
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static class Holder {
        public byte b;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("b");

        Holder h = new Holder();

        h.b = 42;
        check(f.getDouble(h) == 42.0, "positive byte should widen to 42.0");

        h.b = -128;
        check(f.getDouble(h) == -128.0, "Byte.MIN_VALUE should widen to -128.0");

        h.b = 127;
        check(f.getDouble(h) == 127.0, "Byte.MAX_VALUE should widen to 127.0");

        h.b = 0;
        check(f.getDouble(h) == 0.0, "zero byte should yield 0.0");

        h.b = -5;
        check(f.getDouble(h) == f.getByte(h), "getDouble should match getByte value");

        f.setByte(h, (byte) 7);
        check(f.getDouble(h) == 7.0, "getDouble should observe value written by setByte");
    }
}
