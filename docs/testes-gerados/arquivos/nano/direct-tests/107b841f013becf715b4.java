/* @test    @summary Test getBoolean method throws IllegalArgumentException as expected    @run main UnsafeDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Create an instance of UnsafeDoubleFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl(dummyField);
            accessor.getBoolean(new Object());
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
