/*
 * @test
 * @summary Test that getShort(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Instantiate the class under test with isReadOnly = false
        UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = new UnsafeQualifiedStaticIntegerFieldAccessorImpl(dummyField, false);

        // Test that getShort throws IllegalArgumentException
        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private static int dummyField = 0;
}
