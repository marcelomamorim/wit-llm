/*
 * @test
 * @summary Field.getShort on an int field must throw IllegalArgumentException, as UnsafeIntegerFieldAccessorImpl.getShort does
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public int intField = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest t = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field f = UnsafeIntegerFieldAccessorImplWitupTest.class.getDeclaredField("intField");

        boolean thrown = false;
        try {
            short s = f.getShort(t);
            throw new RuntimeException("Expected IllegalArgumentException, got short " + s);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException was not thrown by getShort");
        }

        if (f.getInt(t) != 42) {
            throw new RuntimeException("getInt regression: expected 42");
        }
        if (f.getLong(t) != 42L) {
            throw new RuntimeException("getLong regression: expected 42");
        }
    }
}
