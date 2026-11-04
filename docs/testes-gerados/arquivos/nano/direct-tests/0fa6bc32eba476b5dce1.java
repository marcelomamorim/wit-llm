/* @test    @summary Test getFloat method of UnsafeBooleanFieldAccessorImpl that throws IllegalArgumentException.    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeBooleanFieldAccessorImpl with a dummy Field
            Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
            // Since the constructor is package-private, simulate by reflection if needed
            // But for this test, assume we can instantiate with null or dummy
            jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(dummyField);
            // Call getFloat, which should throw IllegalArgumentException
            accessor.getFloat(new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getFloat correctly threw IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
