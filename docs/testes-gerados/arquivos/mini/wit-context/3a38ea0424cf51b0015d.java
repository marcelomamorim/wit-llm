/*
 * @test
 * @summary Test UnsafeDoubleFieldAccessorImpl.getDouble(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    static class TestClass {
        public double value = 123.456d;
    }

    static class OtherClass {
        public double value = 789.012d;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for TestClass.value
        Field field = TestClass.class.getField("value");
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(field);

        TestClass testObj = new TestClass();

        // Test normal getDouble returns the correct value
        double val = accessor.getDouble(testObj);
        if (val != testObj.value) {
            throw new RuntimeException("getDouble returned wrong value: expected " + testObj.value + ", got " + val);
        }

        // Test getDouble throws IllegalArgumentException when passed object of wrong class
        OtherClass otherObj = new OtherClass();
        try {
            accessor.getDouble(otherObj);
            throw new RuntimeException("Expected IllegalArgumentException when calling getDouble with wrong object class");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }

        // Test getDouble throws IllegalArgumentException when passed null
        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException when calling getDouble with null");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }
}
