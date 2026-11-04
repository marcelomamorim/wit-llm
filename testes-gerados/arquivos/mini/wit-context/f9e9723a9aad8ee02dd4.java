/*
 * @test
 * @summary Test getShort method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = new UnsafeQualifiedStaticDoubleFieldAccessorImpl(dummyField, false);

            try {
                accessor.getShort(null);
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
    private static short dummyField = 0;
}
