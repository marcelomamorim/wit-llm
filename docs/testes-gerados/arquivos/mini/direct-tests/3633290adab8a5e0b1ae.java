/*
 * @test
 * @summary Test that UnsafeObjectFieldAccessorImpl.getDouble always throws IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field dummyField = Dummy.class.getDeclaredField("value");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

        boolean threw = false;
        try {
            accessor.getDouble(new Dummy());
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getDouble");
        }
    }

    static class Dummy {
        int value = 42;
    }
}
