/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCorrectValue();
    }

    public static void testGetDoubleReturnsCorrectValue() throws Exception {
        class TestClass {
            public short value = 123;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
        short expected = 123;
        short result = (short) accessor.getDouble(obj);
        if (result != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
