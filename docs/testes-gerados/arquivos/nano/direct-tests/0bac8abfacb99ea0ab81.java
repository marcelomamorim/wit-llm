/*
 * @test
 * @summary Test getShort method of UnsafeQualifiedStaticFloatFieldAccessorImpl
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of the class under test
            Field dummyField = null; // Not used, as method throws exception directly
            jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl(dummyField, false);
            // Call getShort, expecting an IllegalArgumentException
            accessor.getShort(new Object());
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
