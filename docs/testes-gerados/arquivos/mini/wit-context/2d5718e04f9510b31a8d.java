/*
 * @test
 * @summary Test getDouble(Object) method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class Dummy {
        volatile long value = 123456789L;
    }

    public static void main(String[] args) throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(field, false);

        // Test getDouble returns the long value as double
        double d = accessor.getDouble(dummy);
        if (d != (double) dummy.value) {
            throw new RuntimeException("getDouble returned unexpected value: " + d);
        }

        // Test getDouble throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
