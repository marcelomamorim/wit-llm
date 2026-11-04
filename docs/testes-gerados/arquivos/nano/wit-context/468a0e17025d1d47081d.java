/* @test    @summary Test for getChar method in UnsafeBooleanFieldAccessorImpl to observe exception behavior    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() throws Exception {
        // Setup: create a dummy Field object (using reflection on a dummy class)
        class Dummy {
            public boolean value;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        // Instantiate UnsafeBooleanFieldAccessorImpl with the field
        jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(field);
        // Call getChar and verify it throws IllegalArgumentException
        boolean exceptionThrown = false;
        try {
            accessor.getChar(dummyInstance);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        }
    }
}
