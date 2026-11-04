/* @test    @summary Test getByte method of UnsafeQualifiedIntegerFieldAccessorImpl class to ensure it throws IllegalArgumentException as expected.    @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            // Since the source code indicates getByte always throws, no need to instantiate the class.
            // We simulate calling getByte with a dummy object.
            jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl(dummyField, false);
            accessor.getByte(new Dummy());
            // If no exception is thrown, the test fails.
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes.
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
