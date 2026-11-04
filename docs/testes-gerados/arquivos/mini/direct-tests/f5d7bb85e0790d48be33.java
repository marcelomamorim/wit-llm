/*
 * @test
 * @summary Test getByte method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy static long field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("staticLongField");
        UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new UnsafeQualifiedStaticLongFieldAccessorImpl(field, false);

        // Test that getByte throws IllegalArgumentException
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static long staticLongField = 0L;
    }
}
