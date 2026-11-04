/*
 * @test
 * @summary Test that UnsafeObjectFieldAccessorImpl.getChar always throws IllegalArgumentException
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor
        Field dummyField = Dummy.class.getDeclaredField("c");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);

        try {
            accessor.getChar(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    static class Dummy {
        char c = 'a';
    }
}
