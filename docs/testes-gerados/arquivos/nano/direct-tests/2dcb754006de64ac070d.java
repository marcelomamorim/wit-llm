/*
 * @test
 * @summary Test getChar method of UnsafeCharacterFieldAccessorImpl
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharReturnsCorrectValue();
    }

    private static void testGetCharReturnsCorrectValue() throws Exception {
        class TestObject {
            public char value = 'A';
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("value");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);
        char result = accessor.getChar(obj);
        if (result != 'A') {
            throw new AssertionError("Expected 'A', but got: " + result);
        }
    }
}
