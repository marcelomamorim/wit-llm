/*
 * @test
 * @summary Verify that getBoolean on a volatile long field throws IllegalArgumentException through UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {
    public volatile long value = 42L;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getDeclaredField("value");
        UnsafeQualifiedLongFieldAccessorImplWitupTest t = new UnsafeQualifiedLongFieldAccessorImplWitupTest();

        try {
            f.getBoolean(t);
            throw new AssertionError("expected IllegalArgumentException for getBoolean");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getBoolean(null);
            throw new AssertionError("expected IllegalArgumentException for getBoolean with null obj");
        } catch (IllegalArgumentException expected) {
        }

        if (f.getLong(t) != 42L) {
            throw new AssertionError("getLong must remain functional after getBoolean failures");
        }
    }
}
