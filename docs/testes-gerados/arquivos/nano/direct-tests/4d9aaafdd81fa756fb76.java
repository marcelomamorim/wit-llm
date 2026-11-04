/* @test    @summary Test getChar method of UnsafeStaticFloatFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeStaticFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create an instance with a dummy Field (null, as actual reflection is not used in this test)
            UnsafeStaticFloatFieldAccessorImpl accessor = new UnsafeStaticFloatFieldAccessorImpl(null);
            // Call getChar, expecting IllegalArgumentException
            accessor.getChar(null);
            System.out.println("testGetCharThrowsException: FAILED - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testGetCharThrowsException: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetCharThrowsException: FAILED - unexpected exception: " + t);
        }
    }
}
