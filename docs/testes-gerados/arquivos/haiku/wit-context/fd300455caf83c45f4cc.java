/*
 * @test
 * @summary Field.getBoolean on a byte field must throw IllegalArgumentException through the unsafe byte accessor
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {
    byte b = 7;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeByteFieldAccessorImplWitupTest.class.getDeclaredField("b");
        UnsafeByteFieldAccessorImplWitupTest obj = new UnsafeByteFieldAccessorImplWitupTest();

        boolean thrown = false;
        try {
            f.getBoolean(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getBoolean on byte field");
        }

        Object value = f.get(obj);
        if (!(value instanceof Byte) || ((Byte) value).byteValue() != 7) {
            throw new AssertionError("get returned unexpected value: " + value);
        }
        if (f.getByte(obj) != 7) {
            throw new AssertionError("getByte returned unexpected value");
        }
    }
}
