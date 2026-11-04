/* @test    @summary Test getChar method of UnsafeStaticDoubleFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() {
        try {
            // Create an instance of UnsafeStaticDoubleFieldAccessorImpl with a dummy Field
            Field dummyField = null; // Since getChar always throws, the actual Field is not used
            jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl(dummyField);
            accessor.getChar(null);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getChar() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
