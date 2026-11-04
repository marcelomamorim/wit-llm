/* @test    @summary Test for getInt method in UnsafeQualifiedShortFieldAccessorImpl to verify exception handling and behavior.    @run main UnsafeQualifiedShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntWithInvalidObject();
        testGetIntWithValidObject();
    }

    static class Dummy {
        // Dummy class for testing
    }

    static class TestObject {
        public short value;
    }

    static void testGetIntWithInvalidObject() throws Exception {
        Field dummyField = Dummy.class.getDeclaredField("value");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);
        Object invalidObj = new Object();
        try {
            accessor.getInt(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid object.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for invalid object.");
        }
    }

    static void testGetIntWithValidObject() throws Exception {
        TestObject obj = new TestObject();
        obj.value = 42;
        Field field = TestObject.class.getDeclaredField("value");
        field.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl(field, false);
        int result = accessor.getInt(obj);
        if (result == (int) obj.value) {
            System.out.println("PASS: getInt returned correct value.");
        } else {
            System.out.println("FAIL: getInt returned incorrect value.");
        }
    }
}
