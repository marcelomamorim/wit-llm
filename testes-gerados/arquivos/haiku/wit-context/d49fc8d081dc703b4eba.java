/*
 * @test
 * @summary Verifies that Field.getBoolean on a static byte field throws IllegalArgumentException, which exercises UnsafeStaticByteFieldAccessorImpl.getBoolean through public reflection API
 * @run main UnsafeStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticByteFieldAccessorImplWitupTest {
    public static byte b = 7;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticByteFieldAccessorImplWitupTest.class.getDeclaredField("b");

        try {
            f.getBoolean(null);
            throw new AssertionError("expected IllegalArgumentException from getBoolean");
        } catch (IllegalArgumentException expected) {
        }

        if (f.getByte(null) != 7) {
            throw new AssertionError("getByte returned unexpected value");
        }

        Object boxed = f.get(null);
        if (!(boxed instanceof Byte) || ((Byte) boxed).byteValue() != 7) {
            throw new AssertionError("get returned unexpected value");
        }

        f.setByte(null, (byte) 9);
        if (f.getByte(null) != 9) {
            throw new AssertionError("setByte did not update the field");
        }
    }
}
