/* @test    @summary Test getBoolean method of UnsafeQualifiedStaticLongFieldAccessorImpl    @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create a dummy instance to invoke getBoolean
            // Since getBoolean always throws, no need to set up actual field or object
            jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl(null, false);
            accessor.getBoolean(null);
            // If no exception thrown, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
            System.out.println("getBoolean() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
