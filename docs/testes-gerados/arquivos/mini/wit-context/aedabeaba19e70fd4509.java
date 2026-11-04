/*
 * @test
 * @summary Test UnsafeQualifiedStaticObjectFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Use reflection to get a static Object field to create the accessor
        Field field = TestClass.class.getDeclaredField("staticObjectField");
        UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(field, false);

        // Test getLong throws IllegalArgumentException
        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getLong");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    // Helper class with a static Object field
    static class TestClass {
        static Object staticObjectField = "test";
    }
}
