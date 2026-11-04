/*
 * @test
 * @summary Test UnsafeQualifiedStaticObjectFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Use reflection to get a static Object field to create the accessor
        Field field = TestClass.class.getDeclaredField("staticObjectField");
        UnsafeQualifiedStaticObjectFieldAccessorImpl accessor = new UnsafeQualifiedStaticObjectFieldAccessorImpl(field, false);

        // Test getChar throws IllegalArgumentException
        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getChar");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class TestClass {
        static Object staticObjectField = "test";
    }
}
