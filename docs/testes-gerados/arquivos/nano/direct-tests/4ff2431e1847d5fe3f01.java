/* @test    @summary Test getBoolean method throws IllegalArgumentException as expected    @run main UnsafeStaticIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Create an instance of the class with a dummy Field (null for simplicity, as method does not use it)
            UnsafeStaticIntegerFieldAccessorImpl accessor = new UnsafeStaticIntegerFieldAccessorImpl(null);
            // Call getBoolean, expecting IllegalArgumentException
            accessor.getBoolean(null);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
            System.out.println("getBoolean() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
