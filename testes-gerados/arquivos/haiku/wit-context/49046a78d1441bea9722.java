/*
 * @test
 * @summary Verify Field.getDouble on a volatile byte field (served by UnsafeQualifiedByteFieldAccessorImpl) widens the value and rejects objects of the wrong type
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    volatile byte b;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedByteFieldAccessorImplWitupTest holder = new UnsafeQualifiedByteFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedByteFieldAccessorImplWitupTest.class.getDeclaredField("b");

        holder.b = 42;
        double v = f.getDouble(holder);
        if (v != 42.0) {
            throw new AssertionError("expected 42.0 but was " + v);
        }

        holder.b = -7;
        v = f.getDouble(holder);
        if (v != -7.0) {
            throw new AssertionError("expected -7.0 but was " + v);
        }

        boolean thrown = false;
        try {
            f.getDouble("not an instance of the declaring class");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for wrong object type");
        }
    }
}
