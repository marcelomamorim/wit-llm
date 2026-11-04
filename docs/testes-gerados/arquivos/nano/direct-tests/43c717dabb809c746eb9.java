/* @test    @summary Test getInt method of UnsafeCharacterFieldAccessorImpl class    @run main UnsafeCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntReturnsCorrectValue();
    }

    public static void testGetIntReturnsCorrectValue() throws Exception {
        class TestObject {
            public char testChar = 'A';
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getField("testChar");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);
        int result = accessor.getInt(obj);
        int expected = (int) 'A';
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
