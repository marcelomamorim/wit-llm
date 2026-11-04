/*
 * @test
 * @summary Test getLong method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsCorrectValue();
    }

    public static void testGetLongReturnsCorrectValue() throws Exception {
        class TestObject {
            public long value = 123456789L;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(field, false);
        long result = accessor.getLong(obj);
        if (result != 123456789L) {
            throw new AssertionError("Expected 123456789L but got " + result);
        }
    }
}
