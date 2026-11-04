/* @test    @summary Test getChar method of UnsafeQualifiedCharacterFieldAccessorImpl    @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static class TestObject {
        public char value;
    }

    public static void main(String[] args) throws Exception {
        testGetCharReturnsCorrectValue();
    }

    public static void testGetCharReturnsCorrectValue() throws Exception {
        // Obtain the Field object for 'value'
        Field field = TestObject.class.getField("value");
        // Create an instance of TestObject
        TestObject obj = new TestObject();
        obj.value = 'A';
        // Instantiate UnsafeQualifiedCharacterFieldAccessorImpl with the field
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        // Call getChar and verify the returned value
        char result = accessor.getChar(obj);
        if (result != 'A') {
            throw new AssertionError("Expected 'A', but got: " + result);
        }
        // Change the value and verify again
        obj.value = 'Z';
        result = accessor.getChar(obj);
        if (result != 'Z') {
            throw new AssertionError("Expected 'Z', but got: " + result);
        }
    }
}
