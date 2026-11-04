/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getInt(Object) for correct behavior and exception on invalid object
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class TestClass {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        // Setup: obtain Field and create UnsafeShortFieldAccessorImpl instance
        Field field = TestClass.class.getField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test normal behavior: getInt returns the short value as int
        int intValue = accessor.getInt(obj);
        if (intValue != obj.value) {
            throw new RuntimeException("getInt returned " + intValue + ", expected " + obj.value);
        }

        // Test exception on invalid object type
        Object invalidObj = new Object();
        try {
            accessor.getInt(invalidObj);
            throw new RuntimeException("Expected IllegalArgumentException for invalid object type");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        System.out.println("Test passed.");
    }
}
