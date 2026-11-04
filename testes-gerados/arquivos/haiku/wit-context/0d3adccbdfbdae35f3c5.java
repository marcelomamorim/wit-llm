/*
 * @test
 * @summary Verifies Field.getLong on a volatile int field, which is backed by UnsafeQualifiedIntegerFieldAccessorImpl, returns the value and rejects objects of an unrelated type
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    private volatile int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedIntegerFieldAccessorImplWitupTest holder = new UnsafeQualifiedIntegerFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getDeclaredField("value");

        long result = field.getLong(holder);
        if (result != 42L) {
            throw new AssertionError("Expected 42 but got " + result);
        }

        boolean thrown = false;
        try {
            field.getLong("not a holder");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for unrelated object type");
        }
    }
}
