/* @test    @summary Test getDouble method of UnsafeQualifiedIntegerFieldAccessorImpl class    @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCorrectValue();
    }

    private static void testGetDoubleReturnsCorrectValue() throws Exception {
        class TestClass {
            public int value = 42;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("value");
        // Simulate the creation of UnsafeQualifiedIntegerFieldAccessorImpl
        jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl(field, false);
        double result = accessor.getDouble(obj);
        if (result != 42.0) {
            throw new AssertionError("Expected 42.0 but got " + result);
        }
    }
}
