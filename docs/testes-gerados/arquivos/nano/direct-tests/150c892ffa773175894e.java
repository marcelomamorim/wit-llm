/* @test    @summary Test getByte method of UnsafeBooleanFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        try {
            // Create an instance with a dummy Field (null, as it won't be used)
            UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(null);
            // Call getByte, expecting IllegalArgumentException
            accessor.getByte(new Object());
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getByte() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
