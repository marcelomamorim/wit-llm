/* @test    @summary Test getChar method of UnsafeQualifiedFloatFieldAccessorImpl to ensure it throws IllegalArgumentException.    @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredFields()[0];
            // Since getChar always throws, no need to instantiate the class
            jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);
            accessor.getChar(new Object());
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
