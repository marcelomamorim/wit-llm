/*
 * @test
 * @summary Test UnsafeIntegerFieldAccessorImpl.getLong(Object) for correct behavior and exception
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        int value = 12345;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for int field
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test getLong returns the int value as long
        long longValue = accessor.getLong(obj);
        if (longValue != obj.value) {
            throw new AssertionError("getLong returned " + longValue + ", expected " + obj.value);
        }

        // Test getLong throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException for invalid object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
