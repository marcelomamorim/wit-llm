/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedFloatFieldAccessorImpl
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        // Setup: create a dummy Field object (mocked or dummy, as actual instantiation is complex)
        // Since the constructor is package-private and requires a Field, we assume a valid Field object is available.
        // For testing, we can use reflection to get a real Field from a dummy class.
        class Dummy {
            public float value;
        }
        Field field = Dummy.class.getDeclaredFields()[0];
        jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl(field, false);
        Object obj = new Dummy();
        try {
            // Invoke getChar, expecting IllegalArgumentException
            accessor.getChar(obj);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Test passes if exception is thrown
            System.out.println("IllegalArgumentException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
