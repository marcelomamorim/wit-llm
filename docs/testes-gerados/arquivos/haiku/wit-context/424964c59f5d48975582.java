/*
 * @test
 * @summary Verifies that Field.getByte on a boolean field backed by UnsafeQualifiedBooleanFieldAccessorImpl throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public volatile boolean flag;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedBooleanFieldAccessorImplWitupTest t =
            new UnsafeQualifiedBooleanFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedBooleanFieldAccessorImplWitupTest.class.getField("flag");

        boolean initial = f.getBoolean(t);
        if (initial) {
            throw new AssertionError("expected default false");
        }

        boolean thrown = false;
        try {
            f.getByte(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getByte");
        }
    }
}
