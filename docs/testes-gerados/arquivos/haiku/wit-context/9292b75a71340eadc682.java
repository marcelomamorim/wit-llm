/*
 * @test
 * @summary Field.getShort on a volatile int field must throw IllegalArgumentException (narrowing not allowed)
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public volatile int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedIntegerFieldAccessorImplWitupTest t = new UnsafeQualifiedIntegerFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getDeclaredField("value");

        if (f.getInt(t) != 42) {
            throw new RuntimeException("getInt sanity check failed");
        }

        try {
            f.getShort(t);
            throw new RuntimeException("Expected IllegalArgumentException from getShort");
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
