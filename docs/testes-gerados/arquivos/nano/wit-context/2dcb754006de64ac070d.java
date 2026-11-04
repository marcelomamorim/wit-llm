/*
 * @test
 * @summary Test for UnsafeCharacterFieldAccessorImpl.getChar method behavior
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharWithInvalidObject();
        testGetCharWithValidObject();
    }

    private static void testGetCharWithInvalidObject() throws Exception {
        Field dummyField = String.class.getDeclaredField("value");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(dummyField);
        Object invalidObj = new Object();
        try {
            accessor.getChar(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        }
    }

    private static void testGetCharWithValidObject() throws Exception {
        String testString = "abc";
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(valueField);
        char result = accessor.getChar(testString);
        if (result == 'a') {
            System.out.println("PASS: getChar returned expected value 'a'");
        } else {
            System.out.println("FAIL: getChar returned " + result);
        }
    }
}
