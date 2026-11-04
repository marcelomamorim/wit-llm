/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getLong(Object) method behavior
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    static class TestObj {
        public long value = 123456789L;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);

        // Test getLong returns the correct long value
        long val = accessor.getLong(obj);
        if (val != obj.value) {
            throw new AssertionError("Expected " + obj.value + " but got " + val);
        }

        // Test getLong throws IllegalArgumentException when obj is null
        try {
            accessor.getLong(null);
            throw new AssertionError("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getLong throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
