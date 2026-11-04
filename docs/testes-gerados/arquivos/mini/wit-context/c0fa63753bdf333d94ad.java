/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getDouble(Object) behavior
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class TestClass {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);

        // Normal case: getDouble returns the short value as double
        double d = accessor.getDouble(testObj);
        if (d != (double) testObj.value) {
            throw new RuntimeException("getDouble returned " + d + ", expected " + testObj.value);
        }

        // IllegalArgumentException expected if obj is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // IllegalArgumentException expected if obj is null
        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
