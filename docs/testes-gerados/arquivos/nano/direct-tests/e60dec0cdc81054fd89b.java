/* @test    @summary Test getChar method of UnsafeShortFieldAccessorImpl class    @run main UnsafeShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(dummyField);
            accessor.getChar(new Dummy());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (NoSuchFieldException e) {
            // This should not happen in this test, fail if it does
            throw new AssertionError(e);
        } catch (Throwable t) {
            // Any other unexpected exception
            throw new AssertionError(t);
        }
    }
}
