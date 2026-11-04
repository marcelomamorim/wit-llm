/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedFloatFieldAccessorImpl
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    static class TestObj {
        volatile float f = 123.456f;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("f");
        UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(field, false);

        // Test getDouble returns the float value as double
        double d = accessor.getDouble(obj);
        if (d != obj.f) {
            throw new RuntimeException("getDouble returned " + d + ", expected " + obj.f);
        }

        // Test getDouble throws IllegalArgumentException if null passed
        boolean threw = false;
        try {
            accessor.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("getDouble did not throw IllegalArgumentException for null input");
        }
    }
}
