/* @test    @summary Test getBoolean method of UnsafeQualifiedBooleanFieldAccessorImpl    @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanReturnsCorrectValue();
        testGetBooleanThrowsOnNullObject();
    }

    static class Dummy {
        public boolean value = true;
    }

    static void testGetBooleanReturnsCorrectValue() throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(field, false);
        boolean result = accessor.getBoolean(dummy);
        if (result != dummy.value) {
            throw new AssertionError("Expected " + dummy.value + " but got " + result);
        }
    }

    static void testGetBooleanThrowsOnNullObject() {
        try {
            Field field = Dummy.class.getField("value");
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(field, false);
            accessor.getBoolean(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
