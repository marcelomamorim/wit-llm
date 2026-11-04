/*
 * @test
 * @summary Verifies Field.getDouble on a volatile int field, which is served by UnsafeQualifiedIntegerFieldAccessorImpl, widens values and rejects objects of an incompatible declaring class
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public volatile int value;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedIntegerFieldAccessorImplWitupTest.class.getField("value");

        UnsafeQualifiedIntegerFieldAccessorImplWitupTest obj = new UnsafeQualifiedIntegerFieldAccessorImplWitupTest();

        obj.value = 42;
        double d = f.getDouble(obj);
        if (d != 42.0d) {
            throw new RuntimeException("Expected 42.0 but got " + d);
        }

        obj.value = -7;
        d = f.getDouble(obj);
        if (d != -7.0d) {
            throw new RuntimeException("Expected -7.0 but got " + d);
        }

        obj.value = Integer.MAX_VALUE;
        d = f.getDouble(obj);
        if (d != (double) Integer.MAX_VALUE) {
            throw new RuntimeException("Expected " + (double) Integer.MAX_VALUE + " but got " + d);
        }

        boolean thrown = false;
        try {
            f.getDouble("not-an-instance");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for incompatible object");
        }
    }
}
