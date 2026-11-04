/*
 * @test
 * @summary Test UnsafeQualifiedBooleanFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Prepare a dummy boolean field to create the accessor
        Field booleanField = Dummy.class.getDeclaredField("flag");
        UnsafeQualifiedBooleanFieldAccessorImpl accessor = new UnsafeQualifiedBooleanFieldAccessorImpl(booleanField, false);

        // Call getInt and verify it throws IllegalArgumentException
        try {
            accessor.getInt(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class Dummy {
        public boolean flag = true;
    }
}
