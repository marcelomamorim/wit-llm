/*
 * @test
 * @summary Test getLong method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsCorrectValue();
    }

    public static void testGetLongReturnsCorrectValue() throws Exception {
        class TestObject {
            public short value = 12345;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
        long result = accessor.getLong(obj);
        if (result != 12345L) {
            throw new AssertionError("Expected 12345L, but got " + result);
        }
    }
}
