/*
 * @test
 * @summary Test getChar method of UnsafeQualifiedDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    private static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Create an instance of UnsafeQualifiedDoubleFieldAccessorImpl with dummy values
            Field dummyField = null; // Not used in this test, as method throws before use
            boolean isReadOnly = false;
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, isReadOnly);
            // Call getChar, expecting IllegalArgumentException
            accessor.getChar(new Object());
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getChar threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
