/* @test    @summary Test getFloat method of UnsafeDoubleFieldAccessorImpl class for exception throwing behavior    @run main UnsafeDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatThrowsIllegalArgumentException();
    }

    private static void testGetFloatThrowsIllegalArgumentException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredFields()[0];
        jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl(dummyField);
        try {
            accessor.getFloat(new Object());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
