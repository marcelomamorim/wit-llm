/*
 * @test
 * @summary Test UnsafeQualifiedShortFieldAccessorImpl.getInt(Object) method
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

        // Test getInt returns the short value as int
        int intValue = accessor.getInt(obj);
        if (intValue != obj.value) {
            throw new AssertionError("Expected " + obj.value + " but got " + intValue);
        }

        // Test getInt throws IllegalArgumentException when null passed
        try {
            accessor.getInt(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getInt throws IllegalArgumentException when wrong object type passed
        try {
            accessor.getInt(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
