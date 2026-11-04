/*
 * @test
 * @summary Checks Field.getFloat on byte fields, which is served by UnsafeByteFieldAccessorImpl.getFloat, for valid and wrong-type receivers
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public byte b;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeByteFieldAccessorImplWitupTest.class.getDeclaredField("b");

        UnsafeByteFieldAccessorImplWitupTest holder = new UnsafeByteFieldAccessorImplWitupTest();

        holder.b = 42;
        float positive = field.getFloat(holder);
        if (positive != 42.0f) {
            throw new RuntimeException("Expected 42.0f but got " + positive);
        }

        holder.b = -5;
        float negative = field.getFloat(holder);
        if (negative != -5.0f) {
            throw new RuntimeException("Expected -5.0f but got " + negative);
        }

        holder.b = Byte.MAX_VALUE;
        float max = field.getFloat(holder);
        if (max != 127.0f) {
            throw new RuntimeException("Expected 127.0f but got " + max);
        }

        boolean thrown = false;
        try {
            field.getFloat("not a holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for wrong receiver type");
        }
    }
}
