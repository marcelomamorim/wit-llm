/*
 * @test
 * @summary Test for getDouble method in UnsafeCharacterFieldAccessorImpl
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
        testGetDoubleWithValidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        Field field = String.class.getDeclaredField("value");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);
        Object invalidObj = new Integer(42);
        try {
            accessor.getDouble(invalidObj);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testGetDoubleWithValidObject() throws Exception {
        class Dummy {
            public char c = 'A';
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("c");
        UnsafeCharacterFieldAccessorImpl accessor = new UnsafeCharacterFieldAccessorImpl(field);
        double result = accessor.getDouble(dummy);
        if (result != (double) dummy.c) {
            System.err.println("Unexpected result: " + result);
            System.exit(1);
        }
    }
}
