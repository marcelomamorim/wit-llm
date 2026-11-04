/*
 * @test
 * @summary Test UnsafeQualifiedStaticDoubleFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedStaticDoubleFieldAccessorImpl.class.getDeclaredField("fieldOffset");
        dummyField.setAccessible(true);

        // Construct instance with isReadOnly = false
        UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = new UnsafeQualifiedStaticDoubleFieldAccessorImpl(dummyField, false);

        // Test getByte throws IllegalArgumentException
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
