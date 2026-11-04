/*
 * @test
 * @summary Test UnsafeQualifiedFloatFieldAccessorImpl.getFloat(Object) method
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

        // Test getFloat returns the correct float value
        float val = accessor.getFloat(obj);
        if (val != obj.f) {
            throw new RuntimeException("getFloat returned " + val + ", expected " + obj.f);
        }

        // Test getFloat throws IllegalArgumentException when null is passed
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getFloat throws IllegalArgumentException when object of wrong type is passed
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
