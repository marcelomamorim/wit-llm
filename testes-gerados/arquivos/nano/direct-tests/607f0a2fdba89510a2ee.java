/* @test    @summary Test getInt method of UnsafeQualifiedDoubleFieldAccessorImpl for exception throwing behavior    @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedDoubleFieldAccessorImpl with dummy parameters
            Field dummyField = null; // Not used, as method throws exception before use
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);
            // Call getInt, expecting an IllegalArgumentException
            accessor.getInt(new Object());
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
