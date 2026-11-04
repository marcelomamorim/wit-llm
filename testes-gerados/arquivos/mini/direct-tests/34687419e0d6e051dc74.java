/*
 * @test
 * @summary Test that UnsafeQualifiedLongFieldAccessorImpl.getInt always throws IllegalArgumentException
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (reflection hack)
        Field dummyField = Dummy.class.getDeclaredField("value");

        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(dummyField, false);

        try {
            accessor.getInt(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Test passed
        }
    }

    static class Dummy {
        long value = 42L;
    }
}
