/* @test    @summary Test getShort method of UnsafeIntegerFieldAccessorImpl class    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(dummyField);
            accessor.getShort(new Dummy());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
