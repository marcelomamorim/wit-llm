/* @test    @summary Test getFloat method of UnsafeIntegerFieldAccessorImpl class    @run main UnsafeIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsCorrectValue();
        testGetFloatWithNullObjectThrows();
    }

    static void testGetFloatReturnsCorrectValue() throws Exception {
        class TestClass {
            public int value = 42;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("value");
        jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(field);
        float result = accessor.getFloat(obj);
        if (result != 42.0f) {
            throw new AssertionError("Expected 42.0f, but got " + result);
        }
    }

    static void testGetFloatWithNullObjectThrows() {
        Field field;
        try {
            field = null;
            jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl(field);
            accessor.getFloat(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
