/*
 * @test
 * @summary Test getChar method of UnsafeStaticIntegerFieldAccessorImpl
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create an instance with a dummy Field (null, as actual reflection is not used in this test)
            jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl(null);
            // Call getChar, expecting an IllegalArgumentException
            accessor.getChar(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getChar threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
