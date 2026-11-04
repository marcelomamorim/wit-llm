/*
 * @test
 * @summary Test getByte method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticIntegerFieldAccessorImpl;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Prepare a static integer field to pass to the constructor
        Field field = TestClass.class.getDeclaredField("staticIntField");
        UnsafeQualifiedStaticIntegerFieldAccessorImpl accessor = new UnsafeQualifiedStaticIntegerFieldAccessorImpl(field, false);

        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class TestClass {
        static int staticIntField = 42;
    }
}
