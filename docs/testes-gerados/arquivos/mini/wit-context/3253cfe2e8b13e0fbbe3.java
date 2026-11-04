/*
 * @test
 * @summary Test UnsafeDoubleFieldAccessorImpl.getFloat throws IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(dummyField);

        // Test that getFloat always throws IllegalArgumentException
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private double dummyField = 0.0;
}
