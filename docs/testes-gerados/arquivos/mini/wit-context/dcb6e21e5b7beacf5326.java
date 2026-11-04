/*
 * @test
 * @summary Test that UnsafeQualifiedDoubleFieldAccessorImpl.getFloat always throws IllegalArgumentException
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Create instance with isReadOnly false (value irrelevant for this test)
        UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);

        boolean threw = false;
        try {
            accessor.getFloat(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException not thrown by getFloat");
        }

        System.out.println("Test passed: getFloat throws IllegalArgumentException as expected.");
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private double dummyField;
}
