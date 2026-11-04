/*
 * @test
 * @summary Test UnsafeQualifiedStaticLongFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new UnsafeQualifiedStaticLongFieldAccessorImpl(dummyField, false);

        // Test getByte always throws IllegalArgumentException
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private long dummyField = 0L;
}
