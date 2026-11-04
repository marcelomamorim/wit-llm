/*
 * @test
 * @summary Test that getChar(Object) always throws IllegalArgumentException
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = Dummy.class.getDeclaredField("dummyShort");

        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);

        try {
            accessor.getChar(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public short dummyShort = 1;
    }
}
