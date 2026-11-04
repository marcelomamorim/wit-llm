/*
 * @test
 * @summary Test UnsafeFloatFieldAccessorImpl.getDouble(Object) delegates to getFloat(Object)
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeFloatFieldAccessorImpl;

public class UnsafeFloatFieldAccessorImplWitupTest {

    static class TestObj {
        public float f = 3.14f;
    }

    public static void main(String[] args) throws Exception {
        TestObj to = new TestObj();
        Field f = TestObj.class.getField("f");
        UnsafeFloatFieldAccessorImpl acc = new UnsafeFloatFieldAccessorImpl(f);

        // getDouble should return the same value as getFloat
        double d = acc.getDouble(to);
        float fval = acc.getFloat(to);

        if (d != fval) {
            throw new AssertionError("getDouble returned " + d + ", expected " + fval);
        }

        // test that getDouble throws IllegalArgumentException if obj is null
        boolean threw = false;
        try {
            acc.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getDouble did not throw IllegalArgumentException for null obj");
        }

        // test that getDouble throws IllegalArgumentException if obj is wrong type
        threw = false;
        try {
            acc.getDouble(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getDouble did not throw IllegalArgumentException for wrong obj type");
        }
    }
}
