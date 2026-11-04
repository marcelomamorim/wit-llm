/*
 * @test
 * @summary Test for UnsafeCharacterFieldAccessorImpl.getInt method behavior.
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntWithInvalidObject();
        testGetIntWithValidObject();
    }

    private static void testGetIntWithInvalidObject() throws Exception {
        Field dummyField = String.class.getDeclaredField("value");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl(dummyField);
        Object invalidObj = new Integer(42);
        try {
            accessor.getInt(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testGetIntWithValidObject() throws Exception {
        String testString = "test";
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);
        Object obj = testString;
        jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl(valueField);
        try {
            int result = accessor.getInt(obj);
            System.out.println("PASS: getInt returned: " + result);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
