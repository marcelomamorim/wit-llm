/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getFloat(Object) method behavior
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {

    static class TestObj {
        public float f = 3.14f;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field f = TestObj.class.getField("f");
        UnsafeFloatFieldAccessorImpl accessor = new UnsafeFloatFieldAccessorImpl(f);

        // Test getFloat returns the correct float value
        float val = accessor.getFloat(obj);
        if (val != obj.f) {
            throw new RuntimeException("getFloat returned " + val + ", expected " + obj.f);
        }

        // Test getFloat throws IllegalArgumentException when null passed
        boolean threw = false;
        try {
            accessor.getFloat(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("getFloat did not throw IllegalArgumentException for null input");
        }

        // Test getFloat throws IllegalArgumentException when wrong object type passed
        threw = false;
        try {
            accessor.getFloat(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("getFloat did not throw IllegalArgumentException for wrong object type");
        }
    }
}
