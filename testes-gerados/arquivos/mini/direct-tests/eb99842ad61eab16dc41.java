/*
 * @test
 * @summary Test that UnsafeObjectFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field dummyField = Dummy.class.getDeclaredField("value");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

        boolean thrown = false;
        try {
            accessor.getBoolean(new Dummy());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getBoolean");
        }
    }

    static class Dummy {
        Object value = null;
    }
}
