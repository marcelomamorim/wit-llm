/*
 * @test
 * @summary Test UnsafeQualifiedLongFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(field, false);

        try {
            accessor.getByte(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        public long value = 42L;
    }
}
