/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getFloat(Object) for correct behavior and exception
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class TestClass {
        public short shortField = 123;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("shortField");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);

        // Test getFloat returns the short value as float
        float val = accessor.getFloat(testObj);
        if (val != (float) testObj.shortField) {
            throw new RuntimeException("getFloat returned " + val + ", expected " + testObj.shortField);
        }

        // Test getFloat throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
