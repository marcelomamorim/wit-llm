/*
 * @test
 * @summary Test that UnsafeLongFieldAccessorImpl.getChar always throws IllegalArgumentException
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(dummyField);

        boolean threw = false;
        try {
            accessor.getChar(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException from getChar");
        }

        System.out.println("Test passed: getChar throws IllegalArgumentException as expected.");
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private long dummyField = 0L;
}
