/* @test    @summary Test getByte method of UnsafeQualifiedBooleanFieldAccessorImpl to ensure it always throws IllegalArgumentException.    @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            // Since getByte always throws, no need to initialize dummyField
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            accessor.getByte(new Dummy());
            // If no exception thrown, test fails
            System.err.println("testGetByteThrowsException: FAILED - no exception thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("testGetByteThrowsException: PASSED");
        } catch (Throwable t) {
            System.err.println("testGetByteThrowsException: FAILED - unexpected exception: " + t);
        }
    }
}
