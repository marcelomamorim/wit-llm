/*
 * @test
 * @summary Test UnsafeDoubleFieldAccessorImpl.getLong method throws IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(dummyField);

            // Call getLong and expect IllegalArgumentException
            try {
                accessor.getLong(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private double dummyField = 0.0;
}
