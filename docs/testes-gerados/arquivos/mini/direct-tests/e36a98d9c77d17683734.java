/*
 * @test
 * @summary Test that UnsafeQualifiedShortFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection)
        Field dummyField = Dummy.class.getDeclaredField("dummyShort");

        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);

        try {
            accessor.getBoolean(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class Dummy {
        public short dummyShort = 1;
    }
}
