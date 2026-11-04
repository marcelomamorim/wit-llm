/*
 * @test
 * @summary Test UnsafeStaticLongFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticLongFieldAccessorImpl accessor = new UnsafeStaticLongFieldAccessorImpl(dummyField);

        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static long dummyField = 0L;
}
