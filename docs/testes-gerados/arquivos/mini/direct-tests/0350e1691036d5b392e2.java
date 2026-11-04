/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getFloat(Object) method
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

        // getFloat should return the long value as a float (via getLong)
        float f = accessor.getFloat(obj);
        long l = accessor.getLong(obj);

        if (f != (float) l) {
            throw new AssertionError("getFloat returned " + f + ", expected " + (float) l);
        }

        // Test with null object - should throw IllegalArgumentException
        boolean threw = false;
        try {
            accessor.getFloat(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException when passing null to getFloat");
        }
    }
}
