/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getByte method throws IllegalArgumentException
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(dummyField);

            // The getByte method is expected to always throw IllegalArgumentException
            try {
                accessor.getByte(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private short dummyField;
}
