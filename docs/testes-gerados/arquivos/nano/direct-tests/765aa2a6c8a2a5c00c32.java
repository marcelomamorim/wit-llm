/* @test    @summary Test getShort method of UnsafeBooleanFieldAccessorImpl that throws IllegalArgumentException */
/* @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    public static void testGetShortThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(dummyField);
        try {
            accessor.getShort(new Object());
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
