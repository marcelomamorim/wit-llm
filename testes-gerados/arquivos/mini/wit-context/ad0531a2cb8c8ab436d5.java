/*
 * @test
 * @summary Test UnsafeQualifiedLongFieldAccessorImpl.getByte always throws IllegalArgumentException
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");

        // Create instance with isReadOnly = false (value irrelevant for getByte)
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(dummyField, false);

        try {
            accessor.getByte(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Dummy field to obtain Field instance
    @SuppressWarnings("unused")
    private long dummyField;
}
