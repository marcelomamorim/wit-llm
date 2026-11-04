/*
 * @test
 * @summary Test getShort method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain a static integer field for testing
        Field field = TestClass.class.getDeclaredField("staticIntField");
        UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = new UnsafeQualifiedStaticIntegerFieldAccessorImpl(field, false);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class TestClass {
        static int staticIntField = 42;
    }
}
