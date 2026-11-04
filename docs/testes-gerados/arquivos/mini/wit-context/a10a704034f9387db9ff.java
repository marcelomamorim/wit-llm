/*
 * @test
 * @summary Test UnsafeQualifiedStaticObjectFieldAccessorImpl.getFloat throws expected IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to get a static Object field to create the accessor
        Field field = TestClass.class.getDeclaredField("staticObjectField");
        UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(field, false);

        // The getFloat method must throw IllegalArgumentException always
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Helper class with a static Object field for testing
    static class TestClass {
        static Object staticObjectField = new Object();
    }
}
