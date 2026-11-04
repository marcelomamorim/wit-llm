/*
 * @test
 * @summary Test that getChar(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field dummyField = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
        UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);

        boolean thrown = false;
        try {
            accessor.getChar(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getChar");
        }
    }

    // dummy field to get Field instance
    @SuppressWarnings("unused")
    private float dummyField;
}
