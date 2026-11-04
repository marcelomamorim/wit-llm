/*
 * @test
 * @summary Test that getChar(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Construct the UnsafeQualifiedShortFieldAccessorImpl instance
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);

        // Test getChar(Object) throws IllegalArgumentException
        try {
            accessor.getChar(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }

        // Also test getChar with null argument
        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar with null");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private short dummyField = 0;
}
