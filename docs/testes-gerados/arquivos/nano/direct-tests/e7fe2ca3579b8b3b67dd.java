/*
 * @test
 * @summary Test getInt method of UnsafeStaticObjectFieldAccessorImpl
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntReturnsException();
    }

    private static void testGetIntReturnsException() {
        try {
            Field dummyField = null; // No actual field needed for this test
            UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(dummyField);
            accessor.getInt(null);
            System.out.println("Test failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
