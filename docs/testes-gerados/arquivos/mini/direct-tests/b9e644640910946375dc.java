/*
 * @test
 * @summary Test getLong method of UnsafeQualifiedShortFieldAccessorImpl
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

        // Test getLong returns the short value as long
        long longValue = accessor.getLong(obj);
        if (longValue != obj.value) {
            throw new AssertionError("Expected " + obj.value + " but got " + longValue);
        }

        // Test getLong throws IllegalArgumentException when obj is null
        try {
            accessor.getLong(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getLong throws IllegalArgumentException when obj is wrong type
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
