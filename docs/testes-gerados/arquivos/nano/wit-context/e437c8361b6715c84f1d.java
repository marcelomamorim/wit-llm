/* @test    @summary Test for getDouble method in UnsafeQualifiedShortFieldAccessorImpl    @run main UnsafeQualifiedShortFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
        testGetDoubleWithValidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        Field field = null;
        try {
            field = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);
        Object invalidObj = new Object();
        try {
            accessor.getDouble(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException");
        }
    }

    private static void testGetDoubleWithValidObject() throws Exception {
        class TestClass {
            public short value;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        field.setAccessible(true);
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(field, false);
        try {
            short val = accessor.getShort(obj);
            double result = accessor.getDouble(obj);
            if (result == val) {
                System.out.println("PASS: getDouble returns correct value");
            } else {
                System.out.println("FAIL: getDouble returns incorrect value");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
