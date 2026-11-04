/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class TestClass {
        public short value = 123;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("value");
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        // Normal case: getFloat returns the short value as float
        float f = accessor.getFloat(testObj);
        if (f != (float) testObj.value) {
            throw new AssertionError("Expected getFloat to return " + testObj.value + " but got " + f);
        }

        // Exceptional case: passing an object of incompatible class should throw IllegalArgumentException
        try {
            accessor.getFloat(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible object class");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Exceptional case: passing null should throw IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
