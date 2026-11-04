/* @test    @summary Test for getBoolean method in UnsafeBooleanFieldAccessorImpl    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanReturnsCorrectValue();
        testGetBooleanWithNullObjectThrows();
    }

    static class Dummy {
        public boolean value = true;
    }

    static class DummyFalse {
        public boolean value = false;
    }

    static class DummyNull {
        public boolean value = true;
    }

    static void testGetBooleanReturnsCorrectValue() throws Exception {
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(field);
        Dummy dummy = new Dummy();
        boolean result = accessor.getBoolean(dummy);
        if (result != true) {
            throw new AssertionError("Expected true, but got " + result);
        }
        DummyFalse dummyFalse = new DummyFalse();
        boolean resultFalse = accessor.getBoolean(dummyFalse);
        if (resultFalse != false) {
            throw new AssertionError("Expected false, but got " + resultFalse);
        }
    }

    static void testGetBooleanWithNullObjectThrows() {
        try {
            Field field = Dummy.class.getField("value");
            jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(field);
            accessor.getBoolean(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
