/*
 * @test
 * @summary Test for getFloat method in UnsafeQualifiedShortFieldAccessorImpl
 * @run main UnsafeQualifiedShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedShortFieldAccessorImpl;

public class UnsafeQualifiedShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatWithInvalidObject();
        testGetFloatWithNullObject();
    }

    private static void testGetFloatWithInvalidObject() throws Exception {
        Field dummyField = null; // Placeholder, as actual field setup is complex
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);
        Object invalidObj = new Object();
        try {
            accessor.getFloat(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid object.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for invalid object.");
        }
    }

    private static void testGetFloatWithNullObject() throws Exception {
        Field dummyField = null; // Placeholder, as actual field setup is complex
        UnsafeQualifiedShortFieldAccessorImpl accessor = new UnsafeQualifiedShortFieldAccessorImpl(dummyField, false);
        try {
            accessor.getFloat(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null object.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null object.");
        }
    }
}
