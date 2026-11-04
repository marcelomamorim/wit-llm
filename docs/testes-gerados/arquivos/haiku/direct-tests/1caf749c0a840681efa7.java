/*
 * @test
 * @summary Verify Field.getShort on a byte field returns the sign-extended byte value
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {
    private byte b;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeByteFieldAccessorImplWitupTest.class.getDeclaredField("b");
        f.setAccessible(true);
        UnsafeByteFieldAccessorImplWitupTest t = new UnsafeByteFieldAccessorImplWitupTest();

        check(f, t, (byte) 0, (short) 0);
        check(f, t, (byte) 1, (short) 1);
        check(f, t, (byte) -1, (short) -1);
        check(f, t, (byte) 127, (short) 127);
        check(f, t, (byte) -128, (short) -128);
    }

    private static void check(Field f, Object obj, byte value, short expected) throws Exception {
        f.setByte(obj, value);
        short actual = f.getShort(obj);
        if (actual != expected) {
            throw new RuntimeException("getShort mismatch for byte " + value
                    + ": expected " + expected + " but was " + actual);
        }
    }
}
