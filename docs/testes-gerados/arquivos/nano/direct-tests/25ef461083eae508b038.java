/* @test    @summary Test getBoolean method throws IllegalArgumentException as expected    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Create a dummy instance of UnsafeShortFieldAccessorImpl with a mock Field
            Field mockField = null; // Not used in this test, as method throws before use
            jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(mockField);
            accessor.getBoolean(new Object());
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Passed: getBoolean throws IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
