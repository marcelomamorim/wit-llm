/*
 * @test
 * @summary Verifies Field.getDouble on int fields, which is served by UnsafeIntegerFieldAccessorImpl.getDouble, including IllegalArgumentException for an incompatible receiver
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    private int value;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest holder = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field field = UnsafeIntegerFieldAccessorImplWitupTest.class.getDeclaredField("value");
        field.setAccessible(true);

        holder.value = 0;
        check(field.getDouble(holder) == 0.0d, "zero value");

        holder.value = 42;
        check(field.getDouble(holder) == 42.0d, "positive value");

        holder.value = -7;
        check(field.getDouble(holder) == -7.0d, "negative value");

        holder.value = Integer.MAX_VALUE;
        check(field.getDouble(holder) == (double) Integer.MAX_VALUE, "max value");

        holder.value = Integer.MIN_VALUE;
        check(field.getDouble(holder) == (double) Integer.MIN_VALUE, "min value");

        holder.value = 123456789;
        check(field.getDouble(holder) == 123456789.0d, "exact conversion");

        boolean thrown = false;
        try {
            field.getDouble("not the declaring class");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "incompatible receiver must throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
