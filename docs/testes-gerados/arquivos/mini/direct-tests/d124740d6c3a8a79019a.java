/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getBoolean throws IllegalArgumentException
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

        // Test that getBoolean throws IllegalArgumentException
        try {
            accessor.getBoolean(new TestClass());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Also test getFloat returns the correct value
        TestClass obj = new TestClass();
        float val = accessor.getFloat(obj);
        if (val != 1.0f) {
            throw new RuntimeException("getFloat returned wrong value: " + val);
        }

        // Test get returns Float object wrapping the float value
        Object o = accessor.get(obj);
        if (!(o instanceof Float)) {
            throw new RuntimeException("get did not return Float instance");
        }
        if (((Float) o).floatValue() != 1.0f) {
            throw new RuntimeException("get returned wrong Float value: " + o);
        }
    }
}
