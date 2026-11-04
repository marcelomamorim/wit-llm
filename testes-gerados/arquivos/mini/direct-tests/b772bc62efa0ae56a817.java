/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class TestObj {
        @SuppressWarnings("unused")
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getField("value");
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        // Test getFloat returns the short value as float
        float f = accessor.getFloat(obj);
        if (f != (float) obj.value) {
            throw new AssertionError("Expected getFloat to return " + obj.value + " but got " + f);
        }

        // Test getFloat throws IllegalArgumentException when obj is null
        try {
            accessor.getFloat(null);
            throw new AssertionError("Expected IllegalArgumentException when obj is null");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getFloat throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getFloat(new Object());
            throw new AssertionError("Expected IllegalArgumentException when obj is wrong type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
