/*
 * @test
 * @summary Test UnsafeObjectFieldAccessorImpl.get(Object) for correct behavior and exception on invalid object
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;
import jdk.internal.reflect.UnsafeFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {

    static class Dummy {
        Object value = "test";
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for Dummy.value
        Field field = Dummy.class.getDeclaredField("value");

        // Create UnsafeObjectFieldAccessorImpl instance via UnsafeFieldAccessorImpl constructor
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(field);

        Dummy dummy = new Dummy();

        // Test normal get returns the field value
        Object val = accessor.get(dummy);
        if (!"test".equals(val)) {
            throw new RuntimeException("Expected 'test' but got: " + val);
        }

        // Test get throws IllegalArgumentException when passed object of wrong type
        boolean threwIAE = false;
        try {
            accessor.get(new Object()); // Object not assignable to Dummy
        } catch (IllegalArgumentException e) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("Expected IllegalArgumentException when passing wrong object type");
        }

        System.out.println("Test passed");
    }
}
