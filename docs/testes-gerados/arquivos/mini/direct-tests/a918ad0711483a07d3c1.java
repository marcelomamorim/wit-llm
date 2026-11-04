/*
 * @test
 * @summary Test UnsafeStaticLongFieldAccessorImpl.getBoolean throws IllegalArgumentException
 * @run main UnsafeStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticLongFieldAccessorImpl;

public class UnsafeStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static long field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("staticLongField");
        UnsafeStaticLongFieldAccessorImpl accessor = new UnsafeStaticLongFieldAccessorImpl(field);

        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static long staticLongField = 0L;
    }
}
