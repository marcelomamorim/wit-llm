/*
 * @test
 * @summary Test UnsafeStaticLongFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticLongFieldAccessorImpl accessor = new UnsafeStaticLongFieldAccessorImpl(dummyField);

        // Test getInt always throws IllegalArgumentException
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static long dummyField = 0L;
}
