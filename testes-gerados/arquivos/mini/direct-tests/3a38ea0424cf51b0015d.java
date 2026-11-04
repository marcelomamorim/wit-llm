/*
 * @test
 * @summary Test UnsafeDoubleFieldAccessorImpl.getDouble(Object) method
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    static class TestClass {
        public double value = 123.456d;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("value");
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(field);

        // Test getDouble returns the correct value
        double val = accessor.getDouble(obj);
        if (val != obj.value) {
            throw new RuntimeException("getDouble returned " + val + ", expected " + obj.value);
        }

        // Test getDouble throws IllegalArgumentException when null passed
        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test getDouble throws IllegalArgumentException when wrong type passed
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
