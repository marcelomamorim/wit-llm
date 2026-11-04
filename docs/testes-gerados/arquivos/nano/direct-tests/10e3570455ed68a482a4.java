/* @test    @summary Test getDouble method of UnsafeQualifiedDoubleFieldAccessorImpl    @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsExpectedValue();
        testGetDoubleWithNullObjectThrows();
    }

    private static void testGetDoubleReturnsExpectedValue() throws Exception {
        class TestClass {
            public double value = 42.42;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(field, false);
        double result = accessor.getDouble(obj);
        if (result != 42.42) {
            throw new AssertionError("Expected 42.42, but got " + result);
        }
    }

    private static void testGetDoubleWithNullObjectThrows() {
        try {
            Field field = null;
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(null, false);
            accessor.getDouble(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
