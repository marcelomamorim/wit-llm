/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class TestObj {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getField("value");
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        // Test getDouble returns the short value as double
        double d = accessor.getDouble(obj);
        if (d != (double) obj.value) {
            throw new AssertionError("getDouble returned " + d + ", expected " + (double) obj.value);
        }

        // Test getDouble throws IllegalArgumentException when obj is null
        try {
            accessor.getDouble(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
        }

        // Test getDouble throws IllegalArgumentException when obj is wrong type
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
        }
    }
}
