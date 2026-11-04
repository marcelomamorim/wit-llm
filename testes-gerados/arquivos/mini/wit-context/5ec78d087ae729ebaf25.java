/*
 * @test
 * @summary Test Deflater.setDictionary(byte[], int, int) for valid and invalid inputs
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testSetDictionaryValid();
        testSetDictionaryInvalidOffsets();
    }

    private static void testSetDictionaryValid() {
        Deflater deflater = new Deflater();
        byte[] dict = new byte[10];
        // Fill dictionary with some data
        for (int i = 0; i < dict.length; i++) {
            dict[i] = (byte) i;
        }
        try {
            // Valid call: off=0, len=10
            deflater.setDictionary(dict, 0, 10);
            // Valid call: off=2, len=5
            deflater.setDictionary(dict, 2, 5);
        } finally {
            deflater.end();
        }
    }

    private static void testSetDictionaryInvalidOffsets() {
        Deflater deflater = new Deflater();
        byte[] dict = new byte[5];
        try {
            // off < 0
            try {
                deflater.setDictionary(dict, -1, 3);
                throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off < 0");
            } catch (ArrayIndexOutOfBoundsException expected) {
            }

            // len < 0
            try {
                deflater.setDictionary(dict, 0, -1);
                throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for len < 0");
            } catch (ArrayIndexOutOfBoundsException expected) {
            }

            // off + len > dict.length
            try {
                deflater.setDictionary(dict, 3, 3);
                throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off + len > dict.length");
            } catch (ArrayIndexOutOfBoundsException expected) {
            }
        } finally {
            deflater.end();
        }
    }
}
