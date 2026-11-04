/*
 * @test
 * @summary Test UnsafeQualifiedShortFieldAccessorImpl.getLong(Object) for correct delegation and exception on invalid object
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Dummy {
        public short value = 12345;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for Dummy.value
        Field field = Dummy.class.getField("value");

        // Create instance of UnsafeQualifiedShortFieldAccessorImpl with isReadOnly = false
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        Dummy dummy = new Dummy();

        // Test getLong returns the short value as long
        long longValue = accessor.getLong(dummy);
        if (longValue != dummy.value) {
            throw new AssertionError("getLong returned " + longValue + ", expected " + dummy.value);
        }

        // Test getLong throws IllegalArgumentException when passed object of wrong type
        try {
            accessor.getLong(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        System.out.println("Test passed.");
    }
}
