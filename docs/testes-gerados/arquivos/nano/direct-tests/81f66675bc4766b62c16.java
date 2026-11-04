/*
 * @test
 * @summary Test getDouble method of UnsafeQualifiedFloatFieldAccessorImpl
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsFloatValue();
    }

    private static void testGetDoubleReturnsFloatValue() throws Exception {
        class TestObject {
            public float floatField = 123.45f;
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("floatField");
        UnsafeQualifiedFloatFieldAccessorImpl accessor = new UnsafeQualifiedFloatFieldAccessorImpl(field, false);
        double result = accessor.getDouble(obj);
        if (result != (double) obj.floatField) {
            throw new AssertionError("Expected " + (double) obj.floatField + " but got " + result);
        }
    }
}
