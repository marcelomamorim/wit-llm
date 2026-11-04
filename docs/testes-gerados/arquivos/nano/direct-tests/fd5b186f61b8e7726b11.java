/* @test    @summary Test for UnsafeObjectFieldAccessorImpl.get method to ensure correct object retrieval and exception handling.    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetReturnsObject();
        testGetWithNullObjectThrows();
    }

    private static void testGetReturnsObject() throws Exception {
        class Sample {
            public String value = "test";
        }
        Sample sample = new Sample();
        Field field = Sample.class.getField("value");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(field);
        Object result = accessor.get(sample);
        if (result == null || !result.equals("test")) {
            throw new AssertionError("Expected 'test', but got: " + result);
        }
    }

    private static void testGetWithNullObjectThrows() throws Exception {
        Field field = String.class.getField("value");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(field);
        try {
            accessor.get(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
