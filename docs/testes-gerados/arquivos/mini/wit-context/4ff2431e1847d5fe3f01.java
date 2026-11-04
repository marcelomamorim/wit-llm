/*
 * @test
 * @summary Test for UnsafeStaticIntegerFieldAccessorImpl.getBoolean method throwing IllegalArgumentException
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeStaticIntegerFieldAccessorImpl accessor = new UnsafeStaticIntegerFieldAccessorImpl(dummyField);

            // The getBoolean method must throw IllegalArgumentException always
            try {
                accessor.getBoolean(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    // Dummy field to obtain a Field instance for constructor
    @SuppressWarnings("unused")
    private static int dummyField = 0;
}
