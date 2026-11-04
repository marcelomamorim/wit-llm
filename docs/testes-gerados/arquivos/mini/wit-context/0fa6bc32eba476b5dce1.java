/*
 * @test
 * @summary Test UnsafeBooleanFieldAccessorImpl.getFloat throws IllegalArgumentException
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeBooleanFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(dummyField);

            // The getFloat method must throw IllegalArgumentException unconditionally
            try {
                accessor.getFloat(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private boolean dummyField = true;
}
