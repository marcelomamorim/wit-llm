/*
 * @test
 * @summary Test getDouble(Object) method of UnsafeQualifiedIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    static class TestClass {
        public int intField = 42;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("intField");

        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);

        // Test normal getDouble returns int value as double
        double val = accessor.getDouble(testObj);
        if (val != 42.0) {
            throw new RuntimeException("getDouble returned " + val + ", expected 42.0");
        }

        // Test getDouble throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
