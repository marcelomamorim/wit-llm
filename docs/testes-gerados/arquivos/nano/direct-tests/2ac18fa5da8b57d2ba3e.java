/* @test    @summary Test getChar method of UnsafeStaticByteFieldAccessorImpl for exception throwing    @run main UnsafeStaticByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() throws Exception {
        Field dummyField = null; // Placeholder, as getChar does not use the field parameter
        jdk.internal.reflect.UnsafeStaticByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticByteFieldAccessorImpl(dummyField);
        try {
            accessor.getChar(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getChar threw IllegalArgumentException as expected.");
        }
    }
}
