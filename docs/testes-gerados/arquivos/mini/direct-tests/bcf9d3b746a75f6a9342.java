/*
 * @test
 * @summary Test getDouble method throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy boolean field to pass to constructor
        Field booleanField = Dummy.class.getDeclaredField("flag");
        UnsafeQualifiedBooleanFieldAccessorImpl accessor = new UnsafeQualifiedBooleanFieldAccessorImpl(booleanField, false);

        try {
            accessor.getDouble(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public boolean flag = true;
    }
}
