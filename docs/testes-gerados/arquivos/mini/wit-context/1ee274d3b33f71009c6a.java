/*
 * @test
 * @summary Test UnsafeStaticIntegerFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticIntegerFieldAccessorImpl accessor = new UnsafeStaticIntegerFieldAccessorImpl(dummyField);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static int dummyField = 0;
}
