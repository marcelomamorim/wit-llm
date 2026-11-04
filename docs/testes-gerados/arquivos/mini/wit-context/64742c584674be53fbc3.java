/*
 * @test
 * @summary Test UnsafeStaticDoubleFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticDoubleFieldAccessorImpl accessor = new UnsafeStaticDoubleFieldAccessorImpl(dummyField);

        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static double dummyField = 0.0;
}
