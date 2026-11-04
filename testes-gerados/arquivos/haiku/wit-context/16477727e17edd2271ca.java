/*
 * @test
 * @summary Verify Field.getLong on a byte field, which reaches UnsafeByteFieldAccessorImpl.getLong, sign-extends values and rejects objects of the wrong type
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class Holder {
        byte value;
    }

    public static void main(String[] args) throws Exception {
        Field field = Holder.class.getDeclaredField("value");
        Holder holder = new Holder();

        holder.value = -5;
        check(field.getLong(holder) == -5L, "negative byte must sign-extend to long");

        holder.value = Byte.MAX_VALUE;
        check(field.getLong(holder) == 127L, "Byte.MAX_VALUE must read as 127L");

        holder.value = Byte.MIN_VALUE;
        check(field.getLong(holder) == -128L, "Byte.MIN_VALUE must read as -128L");

        holder.value = 0;
        check(field.getLong(holder) == 0L, "zero byte must read as 0L");

        boolean thrown = false;
        try {
            field.getLong("not a holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "getLong with an object of an unrelated type must throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
