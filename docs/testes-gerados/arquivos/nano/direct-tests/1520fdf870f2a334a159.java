/* @test    @summary Test getDouble method of UnsafeFloatFieldAccessorImpl class    @run main UnsafeFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCorrectValue();
    }

    public static void testGetDoubleReturnsCorrectValue() throws Exception {
        class TestClass {
            public float floatField = 42.5f;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("floatField");
        jdk.internal.reflect.UnsafeFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeFloatFieldAccessorImpl(field);
        float expected = obj.floatField;
        double result = accessor.getDouble(obj);
        if (result != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
