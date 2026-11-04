/*
 * @test
 * @summary Test getBoolean method of UnsafeQualifiedStaticDoubleFieldAccessorImpl throws expected exception
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        try {
            // Create dummy Field instance via reflection to pass to constructor
            Field dummyField = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredFields()[0];
            UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = new UnsafeQualifiedStaticDoubleFieldAccessorImpl(dummyField, false);

            // Call getBoolean and expect IllegalArgumentException
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e, e);
        }
    }

    // Dummy field to obtain a Field instance
    private static final int dummyField = 0;
}
