/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getLong(Object) delegates to getShort(Object)
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class TestObj {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field f = TestObj.class.getField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(f);

        // getLong should return the short value as a long
        long longValue = accessor.getLong(obj);
        if (longValue != (long) obj.value) {
            throw new AssertionError("Expected getLong to return " + obj.value + " but got " + longValue);
        }

        // Also test that getLong throws IllegalArgumentException if null is passed
        try {
            accessor.getLong(null);
            throw new AssertionError("Expected IllegalArgumentException when passing null to getLong");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
