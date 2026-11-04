/*
 * @test
 * @summary Test getFloat(Object) method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    static class TestClass {
        volatile long value = 123L;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeQualifiedLongFieldAccessorImpl accessor = new UnsafeQualifiedLongFieldAccessorImpl(field, false);

        // Normal case: getFloat returns the long value as float
        float f = accessor.getFloat(testObj);
        if (f != (float)123L) {
            throw new RuntimeException("getFloat returned unexpected value: " + f);
        }

        // Exceptional case: passing object of wrong type causes IllegalArgumentException
        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Exceptional case: passing null causes IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
