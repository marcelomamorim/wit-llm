/*
 * @test
 * @summary Verify Field.getInt on a byte field returns the widened byte value, exercising UnsafeByteFieldAccessorImpl.getInt
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {
    private byte b;

    public static void main(String[] args) throws Exception {
        UnsafeByteFieldAccessorImplWitupTest t = new UnsafeByteFieldAccessorImplWitupTest();
        Field f = UnsafeByteFieldAccessorImplWitupTest.class.getDeclaredField("b");
        f.setAccessible(true);

        check(f, t, (byte) 0, 0);
        check(f, t, (byte) -5, -5);
        check(f, t, (byte) 127, 127);
        check(f, t, (byte) -128, -128);
        check(f, t, (byte) 42, 42);
    }

    static void check(Field f, Object o, byte v, int expected) throws Exception {
        f.setByte(o, v);
        int r = f.getInt(o);
        if (r != expected) {
            throw new RuntimeException("getInt returned " + r + " expected " + expected);
        }
        if (f.getByte(o) != v) {
            throw new RuntimeException("getByte inconsistent with set value " + v);
        }
    }
}
