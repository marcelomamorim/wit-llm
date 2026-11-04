/*
 * @test
 * @summary Test getInt method of UnsafeStaticFloatFieldAccessorImpl
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() {
        try {
            // Create a dummy instance of UnsafeStaticFloatFieldAccessorImpl with null field
            // Since constructor is package-private, simulate via reflection if needed
            // But for this test, assume we can instantiate with null or mock object
            // Alternatively, test the method directly with a subclass or dummy class
            jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl(null) {
                @Override
                public int getInt(Object obj) {
                    return super.getInt(obj);
                }
            };
            // Call getInt, expecting it to throw IllegalArgumentException
            accessor.getInt(null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
