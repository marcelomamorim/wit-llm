/*
 * @test
 * @summary Test getBoolean method of UnsafeStaticFloatFieldAccessorImpl throws expected exception
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredFields()[0];
            UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(dummyField);

            // Call getBoolean and expect IllegalArgumentException
            try {
                accessor.getBoolean(null);
                throw new RuntimeException("Expected IllegalArgumentException was not thrown");
            } catch (IllegalArgumentException e) {
                // Expected exception caught, test passes
            }

            System.out.println("Test passed");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed due to unexpected exception");
        }
    }
}
