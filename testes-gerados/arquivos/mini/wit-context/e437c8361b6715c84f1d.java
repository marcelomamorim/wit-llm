/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedShortFieldAccessorImpl for correct behavior and exception
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Dummy {
        public short value = 123;
    }

    public static void main(String[] args) throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        // Test getDouble returns the short value as double
        double d = accessor.getDouble(dummy);
        if (d != (double) dummy.value) {
            throw new AssertionError("getDouble returned " + d + ", expected " + dummy.value);
        }

        // Test getDouble throws IllegalArgumentException if object is not instance of declaring class
        try {
            accessor.getDouble(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        System.out.println("Test passed.");
    }
}
