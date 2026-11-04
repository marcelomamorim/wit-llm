/*
 * @test
 * @summary Test UnsafeQualifiedStaticLongFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to get a static long field for testing
        Field field = TestClass.class.getDeclaredField("staticLongField");
        UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new UnsafeQualifiedStaticLongFieldAccessorImpl(field, false);

        // The getInt method must throw IllegalArgumentException
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class TestClass {
        static long staticLongField = 123L;
    }
}
