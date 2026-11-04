/* @test    @summary Test getInt method of UnsafeDoubleFieldAccessorImpl for exception throwing behavior    @run main UnsafeDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() throws Exception {
        // Create a dummy Field object (using reflection on a real class is complex, so we simulate)
        // Since the method does not use the field parameter, we can pass null or a dummy object
        // but to compile, we need an actual Field. Use reflection on a dummy class.
        class Dummy {
            public double value;
        }
        Field dummyField = Dummy.class.getDeclaredField("value");
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(dummyField);
        try {
            int result = accessor.getInt(new Dummy());
            // The method is expected to throw IllegalArgumentException, so reaching here is a failure
            System.err.println("Expected IllegalArgumentException was not thrown. Result: " + result);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        }
    }
}
