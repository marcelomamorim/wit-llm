/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCorrectValue();
    }

    public static void testGetDoubleReturnsCorrectValue() throws Exception {
        class TestObject {
            public long value = 123456789L;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(field, false);
        long expected = obj.value;
        double result = accessor.getDouble(obj);
        if (result != (double) expected) {
            throw new AssertionError("Expected: " + (double) expected + ", but got: " + result);
        }
    }
}
