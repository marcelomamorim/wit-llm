/*
 * @test
 * @summary Test that getChar method always throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new UnsafeQualifiedStaticLongFieldAccessorImpl(dummyField, false);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getChar");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private long dummyField;
}
