/*
 * @test
 * @summary Test UnsafeQualifiedStaticDoubleFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Create instance with isReadOnly false (value irrelevant for this test)
        UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = new UnsafeQualifiedStaticDoubleFieldAccessorImpl(dummyField, false);

        // Test getLong throws IllegalArgumentException as expected
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getLong");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private static double dummyField = 0.0;
}
