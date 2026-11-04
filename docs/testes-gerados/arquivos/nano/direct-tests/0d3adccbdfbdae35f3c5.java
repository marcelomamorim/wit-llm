/* @test    @summary Test getLong method of UnsafeQualifiedIntegerFieldAccessorImpl class    @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsCorrectValue();
    }

    public static void testGetLongReturnsCorrectValue() throws Exception {
        class TestObject {
            public int value = 12345;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl(field, false);
        long result = accessor.getLong(obj);
        if (result != 12345L) {
            throw new AssertionError("Expected 12345L, but got " + result);
        }
    }
}
