/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getInt(Object) method
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        int value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);

        // Test getInt returns the correct int value
        int intValue = accessor.getInt(testObj);
        if (intValue != 12345) {
            throw new RuntimeException("getInt returned " + intValue + ", expected 12345");
        }

        // Test getInt throws IllegalArgumentException when obj is null
        try {
            accessor.getInt(null);
            throw new RuntimeException("getInt did not throw IllegalArgumentException for null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getInt throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getInt(new Object());
            throw new RuntimeException("getInt did not throw IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
