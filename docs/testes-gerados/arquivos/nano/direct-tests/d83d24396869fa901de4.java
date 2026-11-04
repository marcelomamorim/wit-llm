/*
 * @test
 * @summary Test getShort method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            // Create an instance of UnsafeQualifiedLongFieldAccessorImpl with dummy field
            jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(dummyField, false);
            // Call getShort and expect an IllegalArgumentException
            accessor.getShort(new Dummy());
            System.out.println("Test failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed: IllegalArgumentException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        }
    }
}
