/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getInt(Object) delegates to getShort(Object)
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

        // getInt should return the short value as int
        int intValue = accessor.getInt(obj);
        if (intValue != obj.value) {
            throw new AssertionError("getInt returned " + intValue + ", expected " + obj.value);
        }

        // getInt with null should throw IllegalArgumentException
        try {
            accessor.getInt(null);
            throw new AssertionError("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
