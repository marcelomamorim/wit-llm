/* @test    @summary Test getShort method of UnsafeQualifiedStaticDoubleFieldAccessorImpl for exception throwing behavior    @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    public static void testGetShortThrowsException() {
        try {
            // Create a dummy instance to invoke getShort
            // Since getShort always throws, the input object is irrelevant
            jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = null;
            // Reflection to get the class and constructor
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            Field field = null;
            try {
                field = clazz.getDeclaredField("field");
            } catch (NoSuchFieldException e) {
                // Field not needed for this test, as getShort throws immediately
            }
            // Instantiate the accessor with dummy parameters
            accessor = (jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl) clazz.getDeclaredConstructor(Field.class, boolean.class).newInstance((Field) null, false);
            // Call getShort and expect an IllegalArgumentException
            try {
                accessor.getShort(new Object());
                // If no exception, fail the test
                System.err.println("Expected IllegalArgumentException was not thrown.");
                System.exit(1);
            } catch (IllegalArgumentException e) {
                // Expected exception caught, test passes
                System.out.println("getShort() threw IllegalArgumentException as expected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
