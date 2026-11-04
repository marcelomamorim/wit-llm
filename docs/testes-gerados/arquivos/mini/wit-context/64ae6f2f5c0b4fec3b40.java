/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getDouble(Object) for correct behavior and exception on invalid object
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
        // Create UnsafeIntegerFieldAccessorImpl instance
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test getDouble returns the int value as double
        double d = accessor.getDouble(obj);
        if (d != 42.0) {
            throw new AssertionError("Expected 42.0 but got " + d);
        }

        // Test getDouble throws IllegalArgumentException when passed object of wrong type
        boolean threw = false;
        try {
            accessor.getDouble(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        }

        System.out.println("Test passed.");
    }
}
