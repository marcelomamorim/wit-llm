/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getLong throws IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {

    static class TestClass {
        public float f = 1.0f;
    }

    public static void main(String[] args) throws Exception {
        Field field = TestClass.class.getField("f");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(field);

        try {
            accessor.getLong(new TestClass());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown when obj is null");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
