/*
 * @test
 * @summary Test that getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to the constructor
        Field dummyField = Dummy.class.getDeclaredField("value");
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(dummyField, false);

        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        int value = 42;
    }
}
