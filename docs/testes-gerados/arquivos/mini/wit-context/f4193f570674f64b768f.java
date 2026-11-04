/*
 * @test
 * @summary Test UnsafeQualifiedShortFieldAccessorImpl.getInt(Object) for correct behavior and exception on invalid object
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    static class Dummy {
        public short value = 123;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for Dummy.value
        Field field = Dummy.class.getDeclaredField("value");

        // Create instance of UnsafeQualifiedShortFieldAccessorImpl with isReadOnly = false
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);

        Dummy dummy = new Dummy();

        // Test getInt returns the short value as int
        int intValue = accessor.getInt(dummy);
        if (intValue != dummy.value) {
            throw new AssertionError("getInt returned " + intValue + ", expected " + dummy.value);
        }

        // Test getInt throws IllegalArgumentException when passed object of wrong type
        try {
            accessor.getInt(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        System.out.println("Test passed.");
    }
}
