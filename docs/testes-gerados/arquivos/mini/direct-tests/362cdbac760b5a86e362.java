/*
 * @test
 * @summary Test that UnsafeObjectFieldAccessorImpl.getLong always throws IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field dummyField = Dummy.class.getDeclaredField("value");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

        // Test getLong throws IllegalArgumentException
        try {
            accessor.getLong(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static class Dummy {
        int value = 42;
    }
}
