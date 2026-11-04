/* @test    @summary Test getInt method of UnsafeLongFieldAccessorImpl for exception throwing behavior    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsException();
    }

    private static void testGetIntThrowsException() throws Exception {
        // Create an instance of UnsafeLongFieldAccessorImpl with a dummy Field
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Should not happen, as String has a 'value' field
        }
        jdk.internal.reflect.UnsafeLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeLongFieldAccessorImpl(dummyField);
        try {
            accessor.getInt(new Object());
        } catch (IllegalArgumentException e) {
            System.out.println("getInt correctly threw IllegalArgumentException");
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException was not thrown by getInt");
    }
}
