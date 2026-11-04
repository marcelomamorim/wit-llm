/*
 * @test
 * @summary Test UnsafeObjectFieldAccessorImpl.getLong method throws expected exception
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for testing
        Field dummyField = UnsafeObjectFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Instantiate UnsafeObjectFieldAccessorImpl with the dummy field
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

        // Test getLong throws IllegalArgumentException as expected
        try {
            accessor.getLong(new Object());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getLong");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private Object dummyField;
}
