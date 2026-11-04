/*
 * @test
 * @summary Test UnsafeQualifiedStaticDoubleFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticDoubleFieldAccessorImpl.class.getDeclaredField("field");
        dummyField.setAccessible(true);

        // Construct instance with isReadOnly false (arbitrary)
        UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = new UnsafeQualifiedStaticDoubleFieldAccessorImpl(dummyField, false);

        // Test getInt throws IllegalArgumentException
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught
        }
    }
}
