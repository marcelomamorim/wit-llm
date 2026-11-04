/*
 * @test
 * @summary Test java.util.zip.Inflater.setDictionary method behavior
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDictionaryValid();
        testSetDictionaryInvalidOffset();
        testSetDictionaryInvalidLength();
        testSetDictionaryInvalidOffsetLengthCombination();
    }

    private static void testSetDictionaryValid() throws Exception {
        Inflater inflater = new Inflater();
        byte[] dict = new byte[10];
        for (int i = 0; i < dict.length; i++) {
            dict[i] = (byte) i;
        }
        inflater.setDictionary(dict, 0, dict.length);
        inflater.end();
    }

    private static void testSetDictionaryInvalidOffset() {
        Inflater inflater = new Inflater();
        byte[] dict = new byte[5];
        try {
            inflater.setDictionary(dict, -1, 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            inflater.end();
        }
    }

    private static void testSetDictionaryInvalidLength() {
        Inflater inflater = new Inflater();
        byte[] dict = new byte[5];
        try {
            inflater.setDictionary(dict, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            inflater.end();
        }
    }

    private static void testSetDictionaryInvalidOffsetLengthCombination() {
        Inflater inflater = new Inflater();
        byte[] dict = new byte[5];
        try {
            inflater.setDictionary(dict, 3, 3); // 3 + 3 > 5
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for offset + length > dictionary.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            inflater.end();
        }
    }
}
