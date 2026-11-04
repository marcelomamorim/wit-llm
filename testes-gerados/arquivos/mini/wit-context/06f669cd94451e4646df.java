/*
 * @test
 * @summary Test UnsafeStaticLongFieldAccessorImpl.getShort throws IllegalArgumentException
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
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @SuppressWarnings("unused")
    private static long dummyField = 0L;
}
