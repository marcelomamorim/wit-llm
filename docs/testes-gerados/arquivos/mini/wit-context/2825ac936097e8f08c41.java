/*
 * @test
 * @summary Test UnsafeQualifiedFloatFieldAccessorImpl.getByte always throws IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);

        boolean threw = false;
        try {
            accessor.getByte(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException not thrown by getByte");
        }

        System.out.println("Test passed: getByte throws IllegalArgumentException as expected.");
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private float dummyField;
}
