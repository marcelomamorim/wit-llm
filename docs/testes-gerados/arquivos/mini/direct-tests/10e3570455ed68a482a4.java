/*
 * @test
 * @summary Test UnsafeQualifiedDoubleFieldAccessorImpl.getDouble(Object) method
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    static class TestClass {
        volatile double value = 123.456d;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(field, false);

        // Test getDouble returns the correct value
        double val = accessor.getDouble(obj);
        if (val != obj.value) {
            throw new AssertionError("Expected " + obj.value + " but got " + val);
        }

        // Test getDouble throws IllegalArgumentException when null is passed
        try {
            accessor.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException when wrong type is passed
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
