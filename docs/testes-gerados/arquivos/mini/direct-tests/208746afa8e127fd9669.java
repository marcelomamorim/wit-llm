/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {
    static class TestClass {
        float f = 1.0f;
    }

    public static void main(String[] args) throws Exception {
        Field field = TestClass.class.getDeclaredField("f");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(field);

        try {
            accessor.getShort(new TestClass());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown when obj is null");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
