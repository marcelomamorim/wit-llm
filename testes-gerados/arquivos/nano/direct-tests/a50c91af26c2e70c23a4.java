/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsCorrectValue();
    }

    public static void testGetFloatReturnsCorrectValue() throws Exception {
        class TestObject {
            public int value = 42;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        UnsafeQualifiedIntegerFieldAccessorImpl accessor = new UnsafeQualifiedIntegerFieldAccessorImpl(field, false);
        float result = accessor.getFloat(obj);
        if (result != 42.0f) {
            throw new AssertionError("Expected 42.0f, but got " + result);
        }
    }
}
