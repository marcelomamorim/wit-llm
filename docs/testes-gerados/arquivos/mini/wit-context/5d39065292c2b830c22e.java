/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeIntegerFieldAccessorImpl for correct behavior and exception on invalid object
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        int value = 42;
    }

    public static void main(String[] args) throws Exception {
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test getFloat returns the int value as float
        float f = accessor.getFloat(obj);
        if (f != 42.0f) {
            throw new AssertionError("Expected 42.0f but got " + f);
        }

        // Test getFloat throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getFloat(new Object());
            throw new AssertionError("Expected IllegalArgumentException for invalid object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
