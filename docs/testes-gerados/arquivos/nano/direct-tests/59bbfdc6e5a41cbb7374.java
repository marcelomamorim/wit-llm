/* @test    @summary Test getChar method of UnsafeQualifiedShortFieldAccessorImpl class    @run main UnsafeQualifiedShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);
            accessor.getChar(new Dummy());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
