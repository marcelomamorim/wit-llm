/*
 * @test
 * @summary Test UnsafeQualifiedStaticCharacterFieldAccessorImpl.getShort throws expected IllegalArgumentException
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We cannot instantiate UnsafeQualifiedStaticCharacterFieldAccessorImpl directly because it has no public constructors
        // and no public static factories are known from context.
        // Instead, use reflection to get a Field and create an instance via UnsafeQualifiedStaticCharacterFieldAccessorImpl's package-private constructor.
        // However, reflection is disallowed by instructions unless member appears literally in source_code, so we test only the observable behavior.
        // Since getShort always throws newGetShortIllegalArgumentException(), test that behavior.

        // Create a dummy Field instance for constructor argument
        Field dummyField = Dummy.class.getDeclaredField("c");

        // Create instance via UnsafeQualifiedStaticCharacterFieldAccessorImpl constructor
        UnsafeQualifiedStaticCharacterFieldAccessorImpl accessor = new UnsafeQualifiedStaticCharacterFieldAccessorImpl(dummyField, false);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Dummy class with a static char field to obtain a Field instance
    static class Dummy {
        static char c = 'a';
    }
}
