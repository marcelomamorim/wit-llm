/*
 * @test
 * @summary Test getShort method of UnsafeStaticCharacterFieldAccessorImpl
 * @run main UnsafeStaticCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Create an instance of UnsafeStaticCharacterFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticCharacterFieldAccessorImpl(dummyField);
            // Call getShort and expect an IllegalArgumentException
            accessor.getShort(null);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getShort() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
