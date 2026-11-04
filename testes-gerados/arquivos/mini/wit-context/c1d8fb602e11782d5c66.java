/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getLong(Object) for correct behavior and exception
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class TestClass {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(field);

        // Test normal behavior: getLong returns the short value as long
        long longValue = accessor.getLong(testObj);
        if (longValue != (long) testObj.value) {
            throw new AssertionError("getLong returned " + longValue + ", expected " + testObj.value);
        }

        // Test IllegalArgumentException when passing incompatible object
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException for incompatible object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        System.out.println("Test passed.");
    }
}
