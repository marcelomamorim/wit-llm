/*
 * @test
 * @summary Test UnsafeShortFieldAccessorImpl.getShort(Object) method
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeShortFieldAccessorImpl;

public class UnsafeShortFieldAccessorImplWitupTest {

    static class TestClass {
        short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field f = TestClass.class.getDeclaredField("value");
        UnsafeShortFieldAccessorImpl accessor = new UnsafeShortFieldAccessorImpl(f);

        // Test getShort returns the correct short value
        short val = accessor.getShort(obj);
        if (val != obj.value) {
            throw new RuntimeException("getShort returned " + val + ", expected " + obj.value);
        }

        // Test getShort throws IllegalArgumentException when obj is null
        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test getShort throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getShort(new Object());
            throw new RuntimeException("Expected IllegalArgumentException for wrong obj type");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
