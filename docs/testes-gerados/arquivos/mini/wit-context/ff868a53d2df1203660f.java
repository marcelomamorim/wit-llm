/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getLong(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    static class Dummy {
        public long value = 123L;
    }

    static class Other {
        public long value = 456L;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for Dummy.value
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);

        Dummy dummy = new Dummy();
        long val = accessor.getLong(dummy);
        if (val != 123L) {
            throw new RuntimeException("Expected 123L but got " + val);
        }

        // Test with null object - expect IllegalArgumentException
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test with object of incompatible class - expect IllegalArgumentException
        Other other = new Other();
        try {
            accessor.getLong(other);
            throw new RuntimeException("Expected IllegalArgumentException for incompatible object class");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
