/*
 * @test
 * @summary Test for getInt method in UnsafeQualifiedCharacterFieldAccessorImpl
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntWithInvalidObject();
        testGetIntWithValidObject();
    }

    static void testGetIntWithInvalidObject() throws Exception {
        Field dummyField = String.class.getDeclaredField("value");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(dummyField, false);
        Object invalidObj = new Integer(42);
        try {
            accessor.getInt(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException");
        }
    }

    static void testGetIntWithValidObject() throws Exception {
        class TestObject {
            public char value = 'A';
        }
        TestObject obj = new TestObject();
        Field field = TestObject.class.getDeclaredField("value");
        field.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedCharacterFieldAccessorImpl(field, false);
        int result = accessor.getInt(obj);
        if (result == (int) obj.value) {
            System.out.println("PASS: getInt returned expected value");
        } else {
            System.out.println("FAIL: getInt returned unexpected value: " + result);
        }
    }
}
