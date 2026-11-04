/*
 * @test
 * @summary Test that UnsafeStaticDoubleFieldAccessorImpl.getInt(Object) always throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeStaticDoubleFieldAccessorImpl accessor = new UnsafeStaticDoubleFieldAccessorImpl(dummyField);

        boolean threw = false;
        try {
            accessor.getInt(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException not thrown by getInt(null)");
        }

        System.out.println("Test passed: getInt(Object) throws IllegalArgumentException as expected.");
    }

    // Dummy field to obtain a Field instance
    @SuppressWarnings("unused")
    private static int dummyField = 0;
}
