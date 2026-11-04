/*
 * @test
 * @summary Test getInt method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsCorrectValue();
        testGetIntWithNullObjectThrows();
    }

    static class Dummy {
        public short value = 42;
    }

    static void testGetIntReturnsCorrectValue() throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
        int result = accessor.getInt(dummy);
        if (result != 42) {
            throw new AssertionError("Expected 42, but got " + result);
        }
    }

    static void testGetIntWithNullObjectThrows() {
        try {
            Field field = Dummy.class.getField("value");
            jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
            accessor.getInt(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
