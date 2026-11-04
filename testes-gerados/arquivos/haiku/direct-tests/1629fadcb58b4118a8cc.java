/*
 * @test
 * @summary Field.getBoolean on a volatile int field must throw IllegalArgumentException, and the int value must remain readable afterwards
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public volatile int value = 42;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedIntegerFieldAccessorImplWitupTest t =
            new UnsafeQualifiedIntegerFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            f.getBoolean(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getBoolean");
        }

        if (f.getInt(t) != 42) {
            throw new AssertionError("int value changed after rejected getBoolean");
        }
    }
}
