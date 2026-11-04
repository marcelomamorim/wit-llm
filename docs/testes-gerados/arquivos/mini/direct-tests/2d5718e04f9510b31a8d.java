/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class TestObj {
        volatile long value = 123456789L;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("value");
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(field, false);

        // Test getDouble returns the long value as double (bitwise conversion)
        double d = accessor.getDouble(obj);
        long l = accessor.getLong(obj);

        // Because getDouble returns getLong(obj) as double, the value should be equal to (double) l
        if (d != (double) l) {
            throw new AssertionError("getDouble() did not return expected double value from long field");
        }

        // Test that getDouble throws IllegalArgumentException if obj is null
        boolean threw = false;
        try {
            accessor.getDouble(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getDouble() did not throw IllegalArgumentException when obj is null");
        }

        System.out.println("Test passed");
    }
}
