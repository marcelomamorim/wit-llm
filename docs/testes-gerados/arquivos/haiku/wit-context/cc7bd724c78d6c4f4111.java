/*
 * @test
 * @summary Verify Field.getDouble on a byte field widens the value and rejects objects of unrelated types
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {
    private byte value;

    public static void main(String[] args) throws Exception {
        UnsafeByteFieldAccessorImplWitupTest holder = new UnsafeByteFieldAccessorImplWitupTest();
        Field field = UnsafeByteFieldAccessorImplWitupTest.class.getDeclaredField("value");
        field.setAccessible(true);

        holder.value = -128;
        if (field.getDouble(holder) != -128.0) {
            throw new RuntimeException("getDouble mismatch for -128");
        }

        holder.value = 127;
        if (field.getDouble(holder) != 127.0) {
            throw new RuntimeException("getDouble mismatch for 127");
        }

        holder.value = 0;
        if (field.getDouble(holder) != 0.0) {
            throw new RuntimeException("getDouble mismatch for 0");
        }

        Object other = new Object();
        try {
            field.getDouble(other);
            throw new RuntimeException("expected IllegalArgumentException for unrelated object");
        } catch (IllegalArgumentException expected) {
        }
    }
}
