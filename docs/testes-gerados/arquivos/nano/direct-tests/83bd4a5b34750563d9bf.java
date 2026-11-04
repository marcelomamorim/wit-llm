/* @test    @summary Test getShort method of UnsafeQualifiedBooleanFieldAccessorImpl class    @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            accessor.getShort(new Dummy());
            // Expecting an IllegalArgumentException to be thrown
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Test passes if exception is thrown
            System.out.println("getShort() threw IllegalArgumentException as expected.");
        } catch (NoSuchFieldException e) {
            System.err.println("Test setup failed: " + e);
            System.exit(1);
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
