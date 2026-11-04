/* @test    @summary Test setDictionary method of java.util.zip.Deflater    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testSetDictionaryValid();
        testSetDictionaryInvalidOffset();
        testSetDictionaryInvalidLength();
    }

    static void testSetDictionaryValid() {
        byte[] dict = {1, 2, 3, 4, 5};
        Deflater deflater = new Deflater();
        deflater.setDictionary(dict, 1, 3); // should not throw
    }

    static void testSetDictionaryInvalidOffset() {
        byte[] dict = {1, 2, 3};
        Deflater deflater = new Deflater();
        try {
            deflater.setDictionary(dict, -1, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testSetDictionaryInvalidLength() {
        byte[] dict = {1, 2, 3};
        Deflater deflater = new Deflater();
        try {
            deflater.setDictionary(dict, 1, 3); // len=3, off=1, total=4, should throw
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
