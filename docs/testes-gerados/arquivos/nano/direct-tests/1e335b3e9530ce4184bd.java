/* @test    @summary Test getFloat method of UnsafeStaticDoubleFieldAccessorImpl for exception throwing */
/* @run main UnsafeStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    public static void testGetFloatThrowsException() {
        try {
            // Create an instance of UnsafeStaticDoubleFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl(dummyField);
            float result = accessor.getFloat(null);
            // Since the method always throws, reaching this line indicates failure
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception caught
            System.out.println("testGetFloatThrowsException passed.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
