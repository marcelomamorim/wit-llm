/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getFloat(Object) returns correct float value from short field
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

        // getFloat should return the short value as a float
        float result = accessor.getFloat(obj);
        if (result != (float) obj.value) {
            throw new AssertionError("Expected " + (float) obj.value + " but got " + result);
        }

        // Also test that getFloat throws IllegalArgumentException if null is passed
        try {
            accessor.getFloat(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
