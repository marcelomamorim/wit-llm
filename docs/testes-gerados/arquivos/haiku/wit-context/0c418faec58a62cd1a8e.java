/*
 * @test
 * @summary Verify that Field.getByte on a long field reaches UnsafeLongFieldAccessorImpl.getByte and throws IllegalArgumentException, while Field.getLong still works
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeLongFieldAccessorImplWitupTest.class.getDeclaredField("value");

        try {
            byte b = field.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException, got byte " + b);
        } catch (IllegalArgumentException expected) {
        }

        try {
            byte b = field.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException, got byte " + b);
        } catch (IllegalArgumentException expected) {
        }

        long l = field.getLong(null);
        if (l != 42L) {
            throw new RuntimeException("Unexpected getLong result: " + l);
        }
    }
}
