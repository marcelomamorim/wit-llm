/* @test    @summary Test getDouble method of UnsafeQualifiedCharacterFieldAccessorImpl class    @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleReturnsCharValue();
    }

    private static void testGetDoubleReturnsCharValue() throws Exception {
        class TestObject {
            public char value = 'A';
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        double result = accessor.getDouble(obj);
        if (result != (double) obj.value) {
            throw new AssertionError("Expected " + (double) obj.value + " but got " + result);
        }
    }
}
