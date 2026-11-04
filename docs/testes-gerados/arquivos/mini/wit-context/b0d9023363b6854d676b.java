/*
 * @test
 * @summary Test that UnsafeQualifiedFloatFieldAccessorImpl.getBoolean always throws IllegalArgumentException
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
            accessor.getBoolean(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException from getBoolean");
        }

        System.out.println("Test passed: getBoolean throws IllegalArgumentException as expected.");
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private float dummyField;
}
