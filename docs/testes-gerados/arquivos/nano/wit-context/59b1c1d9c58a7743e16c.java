/*
 * @test
 * @summary Test for getLong method in UnsafeCharacterFieldAccessorImpl
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongWithInvalidObject();
    }

    private static void testGetLongWithInvalidObject() throws Exception {
        Field dummyField = String.class.getDeclaredField("value");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(dummyField);
        Object invalidObject = new Integer(42);
        try {
            accessor.getLong(invalidObject);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
