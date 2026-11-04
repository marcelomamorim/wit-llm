/*
 * @test
 * @summary Test that UnsafeQualifiedDoubleFieldAccessorImpl.getChar always throws IllegalArgumentException
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);

        boolean threw = false;
        try {
            accessor.getChar(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
        }

        System.out.println("Test passed: getChar throws IllegalArgumentException as expected.");
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private double dummyField = 0.0;
}
