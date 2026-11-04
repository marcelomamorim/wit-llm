/*
 * @test
 * @summary Test for jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl.getBoolean(Object) method
 * @run main UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticCharacterFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrows();
    }

    private static void testGetBooleanThrows() {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("dummyChar");
            UnsafeQualifiedStaticCharacterFieldAccessorImpl accessor =
                new UnsafeQualifiedStaticCharacterFieldAccessorImpl(dummyField, false);

            // The getBoolean method must throw IllegalArgumentException
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        } catch (NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Failed to setup test due to reflection error", e);
        }
    }

    // Dummy class with a char field to obtain a Field instance
    static class Dummy {
        public static char dummyChar = 'a';
    }
}
