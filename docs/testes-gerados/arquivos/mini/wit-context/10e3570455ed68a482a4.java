/*
 * @test
 * @summary Test UnsafeQualifiedDoubleFieldAccessorImpl.getDouble(Object) for correct behavior and exception
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    static class Dummy {
        volatile double value = 42.0d;
    }

    public static void main(String[] args) throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(field, false);

        // Test normal getDouble returns the correct value
        double val = accessor.getDouble(dummy);
        if (val != 42.0d) {
            throw new RuntimeException("Expected 42.0 but got " + val);
        }

        // Test getDouble throws IllegalArgumentException if object is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
