/*
 * @test
 * @summary Test UnsafeObjectFieldAccessorImpl.getFloat throws expected IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("value");
            UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

            // The getFloat method must throw IllegalArgumentException always
            try {
                accessor.getFloat(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

            System.out.println("Test passed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed due to unexpected exception: " + e);
        }
    }

    // Dummy class with a field to obtain a Field instance
    static class Dummy {
        Object value = null;
    }
}
