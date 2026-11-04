/*
 * @test
 * @summary Test UnsafeQualifiedBooleanFieldAccessorImpl.getDouble throws expected IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Field.class.getDeclaredFields()[0];
            UnsafeQualifiedBooleanFieldAccessorImpl accessor = new UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);

            // Call getDouble and expect IllegalArgumentException
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e, e);
        }
    }
}
