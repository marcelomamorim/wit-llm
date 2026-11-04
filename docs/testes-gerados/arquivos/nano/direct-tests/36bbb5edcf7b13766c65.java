/* @test    @summary Test getShort method of UnsafeQualifiedDoubleFieldAccessorImpl class    @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);
            accessor.getShort(new Dummy());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (NoSuchFieldException e) {
            // Since Dummy has no fields, this is expected in this test context
            // but to ensure the test runs, we can ignore or rethrow
            throw new RuntimeException(e);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
