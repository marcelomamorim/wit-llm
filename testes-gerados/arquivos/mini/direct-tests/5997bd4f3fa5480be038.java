/*
 * @test
 * @summary Test UnsafeQualifiedShortFieldAccessorImpl.getShort(Object) method
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class TestClass {
        volatile short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        // Test getShort returns the correct short value
        short val = accessor.getShort(obj);
        if (val != obj.value) {
            throw new AssertionError("Expected " + obj.value + " but got " + val);
        }

        // Test getShort throws IllegalArgumentException when obj is null
        try {
            accessor.getShort(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getShort throws IllegalArgumentException when obj is of wrong type
        try {
            accessor.getShort(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
