/*
 * @test
 * @summary Verifies Field.getDouble on a short field, which reaches UnsafeShortFieldAccessorImpl.getDouble, and checks the wrong-receiver IllegalArgumentException path
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    private short value = 42;

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest target = new UnsafeShortFieldAccessorImplWitupTest();
        Field field = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("value");
        field.setAccessible(true);

        check(field.getDouble(target) == 42.0, "getDouble should widen short 42 to 42.0");

        field.setShort(target, (short) -5);
        check(field.getDouble(target) == -5.0, "getDouble should reflect updated short value -5");

        boolean thrown = false;
        try {
            field.getDouble("not the declaring class");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "getDouble with an unrelated receiver should throw IllegalArgumentException");
    }
}
