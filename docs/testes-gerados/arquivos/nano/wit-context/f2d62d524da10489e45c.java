/* @test    @summary Test for getDouble method in UnsafeLongFieldAccessorImpl to verify exception handling and behavior.    @run main UnsafeLongFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeLongFieldAccessorImpl;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
        testGetDoubleWithValidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Should not happen, as String has a "value" field
        }
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(dummyField);
        Object invalidObj = new Object();
        try {
            accessor.getDouble(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        }
    }

    private static void testGetDoubleWithValidObject() throws Exception {
        class TestClass {
            public long value;
        }
        TestClass obj = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeLongFieldAccessorImpl accessor = new UnsafeLongFieldAccessorImpl(field);
        try {
            long val = accessor.getLong(obj);
            double result = accessor.getDouble(obj);
            if (result == (double) val) {
                System.out.println("PASS: getDouble returns correct value.");
            } else {
                System.out.println("FAIL: getDouble does not return correct value.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
