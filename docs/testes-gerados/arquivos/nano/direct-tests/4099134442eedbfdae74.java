/* @test    @summary Test getInt method of UnsafeBooleanFieldAccessorImpl for exception throwing    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredFields()[0];
        jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(dummyField);
        try {
            accessor.getInt(new Object());
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
