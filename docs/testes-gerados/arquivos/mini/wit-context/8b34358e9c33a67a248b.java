/*
 * @test
 * @summary Test for UnsafeQualifiedStaticIntegerFieldAccessorImpl.getBoolean method throwing IllegalArgumentException
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = new UnsafeQualifiedStaticIntegerFieldAccessorImpl(dummyField, false);

        // Test getBoolean throws IllegalArgumentException
        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static int dummyField = 0;
}
