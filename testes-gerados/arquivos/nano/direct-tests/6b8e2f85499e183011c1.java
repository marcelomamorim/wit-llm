/* @test    @summary Test getChar method throws IllegalArgumentException as expected    @run main UnsafeStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() {
        try {
            // Obtain a dummy Field object; since getChar always throws, the actual field is irrelevant
            Field dummyField = null;
            // Instantiate the class with the dummy field
            jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl(dummyField);
            // Call getChar, expecting an IllegalArgumentException
            accessor.getChar(null);
            // If no exception is thrown, the test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
            System.out.println("getChar() threw IllegalArgumentException as expected.");
            System.exit(0);
        } catch (Throwable t) {
            // Unexpected exception, test fails
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
