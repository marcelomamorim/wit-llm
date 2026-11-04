/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getByte throws expected IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field floatField = Float.class.getDeclaredField("value");
            UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(floatField);

            // The getByte method must throw IllegalArgumentException
            try {
                accessor.getByte(new Object());
                throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
            } catch (IllegalArgumentException expected) {
                // Expected exception caught, test passes
            }

            System.out.println("Test passed: getByte throws IllegalArgumentException as expected.");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: unable to get Float.value field", e);
        }
    }
}
