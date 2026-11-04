/*
 * @test
 * @summary Verify that Field.getShort on a static long field throws IllegalArgumentException and that getLong still works
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    public static long value = 42L;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticLongFieldAccessorImplWitupTest.class.getField("value");

        try {
            field.getShort(null);
            throw new AssertionError("Expected IllegalArgumentException from getShort on long field");
        } catch (IllegalArgumentException expected) {
        }

        long read = field.getLong(null);
        if (read != 42L) {
            throw new AssertionError("Unexpected getLong value: " + read);
        }
    }
}
