/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getDouble(Object) returns int value as double
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        int value = 42;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field f = TestClass.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(f);

        double d = accessor.getDouble(obj);
        if (d != 42.0) {
            throw new AssertionError("Expected 42.0 but got " + d);
        }

        // Test with null object to check IllegalArgumentException thrown by getInt
        boolean threw = false;
        try {
            accessor.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when passing null to getDouble");
        }
    }
}
