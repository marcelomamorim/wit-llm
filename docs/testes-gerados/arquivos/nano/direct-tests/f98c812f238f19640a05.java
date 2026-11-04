/*
 * @test
 * @summary Test getShort method of UnsafeDoubleFieldAccessorImpl
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortThrowsException();
    }

    public static void testGetShortThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl(dummyField);
            accessor.getShort(new Dummy());
        } catch (IllegalArgumentException e) {
            // Expected exception
            return;
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
        throw new AssertionError("Expected IllegalArgumentException was not thrown");
    }
}
