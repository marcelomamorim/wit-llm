/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getDouble(Object) for correct behavior and exception
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    static class TestClass {
        public long value = 123456789L;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);

        // Test getDouble returns the long value as double
        double d = accessor.getDouble(testObj);
        if (d != (double) testObj.value) {
            throw new RuntimeException("getDouble returned incorrect value: " + d);
        }

        // Test getDouble throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException if obj is null
        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null obj");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
