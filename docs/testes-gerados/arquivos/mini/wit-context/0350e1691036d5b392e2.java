/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getFloat(Object) for correct behavior and exception
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    static class TestClass {
        public long value = 123456789L;
    }

    public static void main(String[] args) throws Exception {
        // Setup: get Field instance for 'value' in TestClass
        Field field = TestClass.class.getField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test getFloat returns the long value as float
        float f = accessor.getFloat(obj);
        if (f != (float) obj.value) {
            throw new RuntimeException("getFloat returned " + f + ", expected " + (float) obj.value);
        }

        // Test getFloat throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for invalid object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getFloat throws IllegalArgumentException if obj is null
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
