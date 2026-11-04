/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getShort(Object) for correct behavior and exception
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;
import jdk.internal.reflect.UnsafeFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class TestClass {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        // Setup: get Field instance for TestClass.value
        Field field = TestClass.class.getField("value");

        // Create UnsafeShortFieldAccessorImpl instance via UnsafeFieldAccessorImpl constructor
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test normal getShort returns the correct short value
        short val = accessor.getShort(obj);
        if (val != obj.value) {
            throw new AssertionError("getShort returned " + val + ", expected " + obj.value);
        }

        // Test getShort throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getShort(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getShort throws IllegalArgumentException if obj is null
        try {
            accessor.getShort(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
