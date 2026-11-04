/*
 * @test
 * @summary Test that getFloat(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticDoubleFieldAccessorImpl.class.getDeclaredField("field");
        dummyField.setAccessible(true);

        // Instantiate with isReadOnly = false (value irrelevant for getFloat)
        UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = new UnsafeQualifiedStaticDoubleFieldAccessorImpl(dummyField, false);

        // Test getFloat throws IllegalArgumentException as expected
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
