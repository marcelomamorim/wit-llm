/*
 * @test
 * @summary Test UnsafeQualifiedLongFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = UnsafeQualifiedLongFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(dummyField, false);

        boolean caught = false;
        try {
            accessor.getBoolean(new Object());
        } catch (IllegalArgumentException e) {
            caught = true;
        }

        if (!caught) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        }

        System.out.println("Test passed: getBoolean throws IllegalArgumentException as expected.");
    }

    @SuppressWarnings("unused")
    private long dummyField;
}
