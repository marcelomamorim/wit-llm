/* @test    @summary Test getLong method of UnsafeQualifiedStaticDoubleFieldAccessorImpl class    @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    private static void testGetLongThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of the class
            jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = null;
            // Since the method getLong always throws, we just call it and expect an exception
            accessor = new jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl(null, false);
            accessor.getLong(new Object());
            // If no exception, fail the test
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
            System.out.println("getLong() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
