/*
 * @test
 * @summary Test for UnsafeQualifiedStaticBooleanFieldAccessorImpl.getFloat method throwing IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedStaticBooleanFieldAccessorImpl.class.getDeclaredFields()[0];
            // Construct instance with isReadOnly = false
            UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = new UnsafeQualifiedStaticBooleanFieldAccessorImpl(dummyField, false);

            // Call getFloat and expect IllegalArgumentException
            try {
                accessor.getFloat(null);
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
}
