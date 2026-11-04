/*
 * @test
 * @summary Test UnsafeQualifiedObjectFieldAccessorImpl.getFloat throws expected IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    static class Dummy {
        Object field;
    }

    public static void main(String[] args) throws Exception {
        Field f = Dummy.class.getDeclaredField("field");
        UnsafeQualifiedObjectFieldAccessorImpl acc = new UnsafeQualifiedObjectFieldAccessorImpl(f, false);

        // Test getFloat always throws IllegalArgumentException
        try {
            acc.getFloat(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test getFloat throws even if null passed
        try {
            acc.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null input");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
