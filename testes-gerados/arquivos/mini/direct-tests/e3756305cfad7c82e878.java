/*
 * @test
 * @summary Test UnsafeQualifiedBooleanFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy boolean field to pass to the constructor
        Field booleanField = Dummy.class.getDeclaredField("flag");
        UnsafeQualifiedBooleanFieldAccessorImpl accessor = new UnsafeQualifiedBooleanFieldAccessorImpl(booleanField, false);

        try {
            accessor.getLong(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public boolean flag = true;
    }
}
