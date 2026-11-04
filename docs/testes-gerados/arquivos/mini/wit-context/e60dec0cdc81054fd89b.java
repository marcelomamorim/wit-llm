/*
 * @test
 * @summary Test that UnsafeShortFieldAccessorImpl.getChar(Object) always throws IllegalArgumentException
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(dummyField);

        boolean threw = false;
        try {
            accessor.getChar(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException from getChar(Object) was not thrown");
        }

        System.out.println("Test passed: getChar(Object) throws IllegalArgumentException as expected.");
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private short dummyField;
}
