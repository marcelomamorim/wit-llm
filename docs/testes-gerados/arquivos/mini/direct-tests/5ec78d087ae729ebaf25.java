/*
 * @test
 * @summary Test Deflater.setDictionary(byte[], int, int) for valid and invalid inputs
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDictionaryValid();
        testSetDictionaryInvalidNegativeOffset();
        testSetDictionaryInvalidNegativeLength();
        testSetDictionaryInvalidOffsetLengthCombination();
    }

    private static void testSetDictionaryValid() {
        Deflater deflater = new Deflater();
        byte[] dict = new byte[10];
        for (int i = 0; i < dict.length; i++) {
            dict[i] = (byte) i;
        }
        // Use full dictionary
        deflater.setDictionary(dict, 0, dict.length);
        deflater.end();
    }

    private static void testSetDictionaryInvalidNegativeOffset() {
        Deflater deflater = new Deflater();
        byte[] dict = new byte[5];
        try {
            deflater.setDictionary(dict, -1, 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    private static void testSetDictionaryInvalidNegativeLength() {
        Deflater deflater = new Deflater();
        byte[] dict = new byte[5];
        try {
            deflater.setDictionary(dict, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    private static void testSetDictionaryInvalidOffsetLengthCombination() {
        Deflater deflater = new Deflater();
        byte[] dict = new byte[5];
        try {
            // offset + length > dict.length
            deflater.setDictionary(dict, 3, 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for offset+length > dictionary.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }
}
