/*
 * @test
 * @summary Test UnsafeQualifiedFloatFieldAccessorImpl.getInt throws expected IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);

            // The getInt method must throw IllegalArgumentException always
            try {
                accessor.getInt(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private int dummyField;
}
