/* @test    @summary Test getFloat method of UnsafeObjectFieldAccessorImpl for exception throwing behavior    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    public static void testGetFloatThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeObjectFieldAccessorImpl with a dummy Field
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            // Call getFloat and expect an IllegalArgumentException
            accessor.getFloat(new Dummy());
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
