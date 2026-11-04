/*
 * @test
 * @summary Test that UnsafeQualifiedDoubleFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection on a known field)
        Field doubleField = Dummy.class.getDeclaredField("value");
        UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(doubleField, false);

        // Test getBoolean throws IllegalArgumentException
        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static class Dummy {
        public double value = 1.0d;
    }
}
