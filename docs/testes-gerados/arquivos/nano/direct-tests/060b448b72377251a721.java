/* @test    @summary Test getBoolean method throws IllegalArgumentException as expected    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Create a dummy Field object (null for simplicity, as method does not use it)
            Field dummyField = null;
            jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(dummyField);
            accessor.getBoolean(new Object());
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("getBoolean() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
