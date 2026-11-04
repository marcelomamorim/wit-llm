/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getInt(Object) for correct behavior and exception
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        int value = 42;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for int field 'value'
        Field field = TestClass.class.getDeclaredField("value");
        field.setAccessible(true);

        // Create UnsafeIntegerFieldAccessorImpl instance
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test getInt returns the correct int value
        int intValue = accessor.getInt(obj);
        if (intValue != 42) {
            throw new RuntimeException("getInt returned " + intValue + ", expected 42");
        }

        // Test getInt throws IllegalArgumentException when passed an incompatible object
        try {
            accessor.getInt(new Object());
            throw new RuntimeException("getInt did not throw IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        // Test getInt throws IllegalArgumentException when passed null
        try {
            accessor.getInt(null);
            throw new RuntimeException("getInt did not throw IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
