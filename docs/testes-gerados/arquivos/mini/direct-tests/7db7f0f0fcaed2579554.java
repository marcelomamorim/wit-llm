/*
 * @test
 * @summary Test that UnsafeObjectFieldAccessorImpl.getFloat(Object) always throws IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for testing
        Field dummyField = Dummy.class.getDeclaredField("value");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

        try {
            accessor.getFloat(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown when obj is null");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        int value = 42;
    }
}
