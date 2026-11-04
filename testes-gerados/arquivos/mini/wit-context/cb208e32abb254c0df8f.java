/*
 * @test
 * @summary Test that UnsafeQualifiedShortFieldAccessorImpl.getByte always throws IllegalArgumentException
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedShortFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Create instance with isReadOnly = false (value irrelevant for getByte)
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);

        try {
            accessor.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private short dummyField = 0;
}
