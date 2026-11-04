/*
 * @test
 * @summary Test for getLong method in UnsafeObjectFieldAccessorImpl
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongWithNullObject();
        testGetLongWithInvalidObject();
    }

    private static void testGetLongWithNullObject() {
        try {
            Field dummyField = null; // não usamos, apenas para evitar compilação
            UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);
            accessor.getLong(null);
            System.out.println("FAIL: Expected NullPointerException for null object");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null object");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null object: " + t);
        }
    }

    private static void testGetLongWithInvalidObject() {
        try {
            Field dummyField = null; // não usamos, apenas para evitar compilação
            UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(dummyField);
            Object invalidObject = new Object();
            accessor.getLong(invalidObject);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid object");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected for invalid object");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for invalid object: " + t);
        }
    }
}
