/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsShortValue();
    }

    public static void testGetFloatReturnsShortValue() throws Exception {
        class TestObject {
            public short value = 123;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        boolean isReadOnly = false;
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, isReadOnly);
        float result = accessor.getFloat(obj);
        if (result != (float) obj.value) {
            throw new AssertionError("Expected " + (float) obj.value + " but got " + result);
        }
    }
}
