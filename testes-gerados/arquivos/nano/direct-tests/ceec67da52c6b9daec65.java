/* @test    @summary Test for Inflater.setDictionary method behavior.    @run main InflaterWitupTest */

import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) {
        testSetDictionaryWithValidInput();
        testSetDictionaryWithInvalidOffset();
        testSetDictionaryWithInvalidLength();
    }

    private static void testSetDictionaryWithValidInput() {
        Inflater inflater = new Inflater();
        byte[] dict = {1, 2, 3, 4, 5};
        try {
            inflater.setDictionary(dict, 0, dict.length);
            System.out.println("testSetDictionaryWithValidInput passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid input", e);
        }
    }

    private static void testSetDictionaryWithInvalidOffset() {
        Inflater inflater = new Inflater();
        byte[] dict = {1, 2, 3, 4, 5};
        boolean thrown = false;
        try {
            inflater.setDictionary(dict, -1, 3);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative offset");
        }
        System.out.println("testSetDictionaryWithInvalidOffset passed");
    }

    private static void testSetDictionaryWithInvalidLength() {
        Inflater inflater = new Inflater();
        byte[] dict = {1, 2, 3, 4, 5};
        boolean thrown = false;
        try {
            inflater.setDictionary(dict, 2, 4);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid length");
        }
        System.out.println("testSetDictionaryWithInvalidLength passed");
    }
}
