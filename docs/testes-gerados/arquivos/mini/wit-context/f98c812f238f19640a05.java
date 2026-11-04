/*
 * @test
 * @summary Test UnsafeDoubleFieldAccessorImpl.getShort throws expected IllegalArgumentException
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

            // The getShort method must throw IllegalArgumentException always
            try {
                accessor.getShort(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private double dummyField = 0.0;
}
