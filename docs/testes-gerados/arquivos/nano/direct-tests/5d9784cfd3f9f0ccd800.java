/* @test    @summary Test getLong method of UnsafeQualifiedCharacterFieldAccessorImpl class    @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongReturnsCorrectValue();
    }

    public static void testGetLongReturnsCorrectValue() throws Exception {
        class TestObject {
            public char value = 'A';
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        long result = accessor.getLong(obj);
        long expected = (long) accessor.getChar(obj);
        if (result != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
