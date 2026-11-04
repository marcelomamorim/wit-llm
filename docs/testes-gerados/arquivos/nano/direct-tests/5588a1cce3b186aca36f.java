/*
 * @test
 * @summary Test getFloat method of UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatReturnsCharValue();
    }

    public static void testGetFloatReturnsCharValue() throws Exception {
        class TestObject {
            public char value = 'A';
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        float result = accessor.getFloat(obj);
        if (result != (float) obj.value) {
            throw new AssertionError("Expected " + (float) obj.value + " but got " + result);
        }
    }
}
