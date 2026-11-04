/* @test    @summary Test for UnsafeQualifiedObjectFieldAccessorImpl.get method    @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetReturnsObject();
        testGetWithNullObjectThrows();
    }

    static class Dummy {
        public String value = "test";
    }

    static void testGetReturnsObject() throws Exception {
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        field.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(field, false);
        Object result = accessor.get(dummy);
        if (result == null || !result.equals("test")) {
            throw new AssertionError("Expected 'test', got: " + result);
        }
    }

    static void testGetWithNullObjectThrows() throws Exception {
        Field field = Dummy.class.getDeclaredField("value");
        field.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(field, false);
        try {
            accessor.get(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
