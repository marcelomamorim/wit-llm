/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsLongValue();
    }

    public static void testGetFloatReturnsLongValue() throws Exception {
        class TestObject {
            public long value = 123456789L;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(field, false);
        float result = accessor.getFloat(obj);
        long expected = accessor.getLong(obj);
        if (result != (float) expected) {
            throw new AssertionError("Expected float value: " + (float) expected + ", but got: " + result);
        }
    }
}
