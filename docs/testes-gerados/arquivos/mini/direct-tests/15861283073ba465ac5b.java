/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedLongFieldAccessorImpl
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

        // Test getFloat returns the long value as float
        float f = accessor.getFloat(obj);
        long l = accessor.getLong(obj);

        if (f != (float) l) {
            throw new AssertionError("getFloat did not return the float conversion of the long field value");
        }

        // Test getFloat throws IllegalArgumentException when obj is null
        boolean threw = false;
        try {
            accessor.getFloat(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getFloat did not throw IllegalArgumentException when obj is null");
        }

        System.out.println("Test passed");
    }
}
