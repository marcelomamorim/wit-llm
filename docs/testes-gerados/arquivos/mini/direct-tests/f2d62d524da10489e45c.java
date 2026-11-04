/*
 * @test
 * @summary Test UnsafeLongFieldAccessorImpl.getDouble(Object) method
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
        Field f = TestObj.class.getField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(f);

        // Test getDouble returns the long value as double (via getLong)
        double d = accessor.getDouble(obj);
        if (d != (double) obj.value) {
            throw new AssertionError("getDouble returned " + d + ", expected " + (double) obj.value);
        }

        // Test getDouble throws IllegalArgumentException if obj is null
        boolean threw = false;
        try {
            accessor.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getDouble did not throw IllegalArgumentException for null object");
        }
    }
}
