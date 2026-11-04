/*
 * @test
 * @summary Verifies that Field.getBoolean on an int field, which dispatches to UnsafeIntegerFieldAccessorImpl.getBoolean, throws IllegalArgumentException
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {
    public int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeIntegerFieldAccessorImplWitupTest t = new UnsafeIntegerFieldAccessorImplWitupTest();
        Field f = UnsafeIntegerFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            f.getBoolean(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on int field");
        }

        if (f.getInt(t) != 42) {
            throw new RuntimeException("int accessor should still read the value after getBoolean failure");
        }
    }
}
