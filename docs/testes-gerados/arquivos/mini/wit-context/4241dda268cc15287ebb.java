/*
 * @test
 * @summary Test UnsafeQualifiedFloatFieldAccessorImpl.getShort throws IllegalArgumentException
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrows();
    }

    private static void testGetShortThrows() {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("f");
            UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);
            accessor.getShort(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: field not found", e);
        }
    }

    static class Dummy {
        public float f = 1.0f;
    }
}
