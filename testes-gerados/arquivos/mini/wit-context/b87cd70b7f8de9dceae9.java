/*
 * @test
 * @summary Test UnsafeStaticShortFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticShortFieldAccessorImpl accessor = new UnsafeStaticShortFieldAccessorImpl(dummyField);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static short dummyField = 0;
}
